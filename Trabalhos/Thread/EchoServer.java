import java.io.*; 
import java.net.*; 
import java.util.*; 
public class EchoServer 
{ 
public static void main(String[] args ) 
{ 
try 
{ 
ServerSocket s = new ServerSocket(8189); //faz um bind (para dizer a porta que vai conversar). 
Socket incoming = s.accept( ); /*FULL DUPLEX. eh o tubo(composto pelo ip de destino e do receptor, e pelas portas dos processos) 
								 que é aberto para trocar os dados (eh aquele tubo do http) 
								 Eh aqui que o cliente se conecta - tree hand shake*/
try 
{ 
//Os dois (InputStream,OutputStream) estao relacionados apenas com um socket
InputStream inStream = incoming.getInputStream(); //Para manipular o tubo (trata os dados de entrada, os dados que vc vai receber).
OutputStream outStream = incoming.getOutputStream(); //Para manipular o tubo (trata os dados que vc vai mandar).

Scanner in = new Scanner(inStream); //como n eh entrada de teclado, manda o certo . in variavel que contem tudo que ta chegando no meu socket.
PrintWriter out = new PrintWriter(outStream, true /* autoFlush */); //Manda para o cliente a msg. autoFlush = no enter, n espera o buffer encher, manda logo a msg
out.println( "Hello! Enter BYE to exit." ); //essa msg aparece logo qd o cliente se conecta

// echo client input 
boolean done = false; //para gerenciar a conversa, encerra-la
while (!done && in.hasNextLine()) 
{ 
String line = in.nextLine(); 
out.println("Echo: " + line); 
if (line.trim().equals("BYE")) // qd a string for igual a BYE, fecha a conversa
	done = true; 
} 
} 
finally 
{ 
incoming.close(); 
} 
} 
catch (IOException e) 
{ 
e.printStackTrace(); 
} 
} 
} 
