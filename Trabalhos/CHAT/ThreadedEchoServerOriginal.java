import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedEchoServerOriginal
{  
	// CONTADOR GERAL PARA OS SERVLETS QUE VAI SER USADO LA NO FOR DA OUTRA CLASSE
	static int counter = 0;
	static ArrayList<PrintWriter> listaDeOuts = new ArrayList<PrintWriter>();

	public static void main(String[] args)
	{

		PrintWriter out;

		try
		{
			ServerSocket s = new ServerSocket(8189);

			for (;;)
			{
				// CRIA O SOCKET E A SUA SAIDA DE DADOS ESPECIFICA.
				Socket socketAtual = s.accept();
				System.out.println("O cliente " + socketAtual.getInetAddress().getHostAddress() + " se conectou");
				out = new PrintWriter(socketAtual.getOutputStream(), true /* autoFlush */);
				++counter;

				// ADICIONA A REFERENCIA A SAIDA DE DADOS NA LISTA
				listaDeOuts.add(out);
				
				new ThreadedEchoHandler(socketAtual).start();

			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

class ThreadedEchoHandler extends Thread 
{
	// TEMOS QUE DEIXAR APENAS 1 IN PARA TODOS E VARIOS OUT PARA CADA UM
	
	Socket socketAtual;
	
	public ThreadedEchoHandler(Socket i)
	{
		this.socketAtual = i;
	}

	public void run()
	{

		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socketAtual.getInputStream()));

			ThreadedEchoServerOriginal.listaDeOuts.get(ThreadedEchoServerOriginal.counter - 1).println("Hello! Enter BYE to exit.");

			boolean done = false;
			while (!done)
			{
				String str = in.readLine();
				if (str == null)
					done = true;
				else
				{
					// MOSTRA APENAS PARA O SERVIDOR
					System.out.println(socketAtual.getInetAddress().getHostAddress() + " diz: " + str);

					// PERCORRE OS OUT DOS SERVLETS DIRECIONANDO AS MENSAGENS
					for (int b = 0; b < ThreadedEchoServerOriginal.counter; b++)
						ThreadedEchoServerOriginal.listaDeOuts.get(b).println(socketAtual.getInetAddress().getHostAddress() + " diz: " + str);
					
					if (str.trim().equals("BYE"))
						done = true;
				}

			}
			socketAtual.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

