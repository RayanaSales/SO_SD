package teste;
import java.io.*;
import java.net.*;
public class Cliente
{ public static void main(String[] arg)
  { double numero;
    int c, total = (int)(10*Math.random()); // gera um número float que será convertido para int que vai controlar o loop.
    Socket s = null;

	// Devemos criar um HTTP link, vamos receber linha por linha na entrada, ai vamos definir as nossas próprias tags.
	// O cliente insere : <negrito>Esse texto tem que ser Negrito! </negrito> --> Esse texto tem que aparecer em negrito na tela do cliente.
	// Usar o JOptionPanel

    try
    { 
      System.out.println("Conectando...");
      s = new Socket("192.168.96.125",6789);
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());
      System.out.println("Conectado. Enviando "+total+" números...");
      for(c=0; c<total; c++)
      { 
	numero = 100.0*Math.random();
        System.out.println("Enviando "+numero);
        out.writeDouble(numero);
      }
      out.flush();
      out.writeDouble(-1.0);
      System.out.println("Somatório = "+in.readDouble());
      }
      catch(Exception e)
      { System.out.println("Erro: "+e.getMessage()); }
      finally
      { try{if(s!=null) s.close();}catch(Exception e2){}    }
      System.out.println("Conexão encerrada");
      try{System.in.read();}catch(Exception e3){}
  }
}
