import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Cliente
{ public static void main(String[] arg)
  { 
    String numero;
    int c;
	// total = (int)(10*Math.random());
    Socket s = null;
	Scanner scanner = new Scanner(System.in);
	// Devemos criar um HTTP link, vamos receber linha por linha na entrada, ai vamos definir as nossas proprias tags.
	// O cliente insere : <negrito>Esse texto tem que ser Negrito! </negrito> --> Esse texto tem que aparecer em negrito na tela do cliente.
	// Usar o JOptionPanel

    try
    { 
      System.out.println("Conectando...");
      s = new Socket("192.168.96.125",6789);
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());
      //System.out.println("Conectado. Enviando "+total+" numeros...");
      for(c=0; c<10; c++)
      { 
	System.out.println("Esperando um texto: ");
	numero = scanner.nextLine();
	//numero = 100.0*Math.random();
        System.out.println("Enviando "+ numero);
        out.writeUTF(numero);
      }
      out.flush();
      out.writeDouble(-1.0);
      System.out.println("Somatorio = "+in.readDouble());
      }
      catch(Exception e)
      { System.out.println("Erro: "+e.getMessage()); }
      finally
      { try{if(s!=null) s.close();}catch(Exception e2){}    }
      System.out.println("Conexao encerrada");
      try{System.in.read();}catch(Exception e3){}
  }
}
