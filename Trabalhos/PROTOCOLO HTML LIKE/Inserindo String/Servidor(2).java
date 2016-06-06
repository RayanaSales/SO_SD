import java.io.*;
import java.net.*;

public class Servidor
{ 
	public static void main(String[] arg)
  { ServerSocket s;
    try
    { s = new ServerSocket(6789);
      System.out.println("Servidor iniciado na porta 6789");
      while(true)
      { Socket cliente = s.accept();
        System.out.println("Conexao estabelecida "+"("+cliente+")");

	//podemos enviar dados com esse metodo tb, q eh mais elaborado que o getinputStream. Aqui tem tipagem, podemos mandar double.
        DataInputStream in = new DataInputStream(cliente.getInputStream());
        DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

        String numero;

        numero = in.readUTF(); 
	out.writeUTF("Escreva adeus para sair.");
        while(!numero.equals("adeus"))
        { 
          System.out.println("Valor recebido: "+numero);
          numero = in.readUTF();
        }
          cliente.close();
          System.out.println("Conexao encerrada.");
     }
    }
     catch(Exception e)
      { System.out.println("Erro: "+e.getMessage()); }
   }
}
