package teste;
import java.io.*;
import java.net.*;
public class Servidor
{ public static void main(String[] arg)
  { ServerSocket s;
    try
    { 
	s = new ServerSocket(6789);
      System.out.println("Servidor iniciado na porta 6789");
      while(true)
      { 
	Socket cliente = s.accept();
        System.out.println("Conexão estabelecida "+"("+cliente+")");

	// fluxo de entrada de dados, ele encapsula o getInputStream() e isso permite realizar a tipagem de dados, ou seja, mandar um integer, um double...
	// Essa leitura é realizada do cliente
        DataInputStream in = new DataInputStream(cliente.getInputStream());
	DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

        double numero, soma = 0.0;

        // É síncrono, isso faz com que fiquemos esperando que ele digite nesse passo.
        numero = in.readDouble();
	
        while(numero != -1.0)
        { 
	  soma += numero;
          System.out.println("Valor recebido: "+numero+ " \t - parcial = "+soma);
          numero = in.readDouble();
        }
	// Da mesma forma que pegamos pela tipagem, podemos enviar pela tipagem também.
          out.writeDouble(soma);
          cliente.close();
          System.out.println("Conexão encerrada.");
     }
    }
     catch(Exception e)
      { System.out.println("Erro: "+e.getMessage()); }
   }
}
