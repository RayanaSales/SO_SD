/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 */

import java.io.*;
import java.net.*;

public class ThreadedEchoServer
{  public static void main(String[] args )
   {  int i = 1;
      try
      {  ServerSocket s = new ServerSocket(8189); //servidor

         for (;;)
         {  Socket incoming = s.accept( ); //cria varios sockets q estao conectados ao msm servidor (s), e espera varios clientes
            System.out.println("Spawning " + i); //to aq com o socket aberto
            new ThreadedEchoHandler(incoming, i).start(); //objeto sem referencia (ele apenas existe), passo como parametro o socket e o contador (qtd usuarios), comece
            i++;										//start é um metodo da classe thread, e ele invoca o metodo run.
														//para fazer um chat onde todos vem as msgs, coloca a referencia em uma lista antes da referencia ser perdida.
         }
      }
      catch (Exception e)
      {  System.out.println(e);
      }
   }
}

class ThreadedEchoHandler extends Thread 
{  
	public ThreadedEchoHandler(Socket i, int c) //manda a lista aq no lugar de i (a lista com todas as referencias dos sockets)
   { incoming = i; counter = c; }

   public void run()
   {  try
      {  BufferedReader in = new BufferedReader
            (new InputStreamReader(incoming.getInputStream()));
         PrintWriter out = new PrintWriter
            (incoming.getOutputStream(), true /* autoFlush */); //usa esse incoming para todas as posições da lista.

		//começa a mostrar as msgs aq
		
         out.println( "Hello! Enter BYE to exit." );

         boolean done = false;
         while (!done)
         {  String str = in.readLine();
             System.out.println (i + " - " +str);
            if (str == null) done = true;
            else
            {  out.println("Echo (" + counter + "): " + str);

               if (str.trim().equals("BYE"))
                  done = true;
            }
         }
         incoming.close();
      }
      catch (Exception e)
      {  System.out.println(e);
      }
   }

   private Socket incoming;
   private int counter;
}

