package recebe;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Recebe //compilado primeiro
{
	static int idMsg = 0 ;
	
	public static void main(String[] args) throws Exception
	{
		byte[] buffer = new byte[1024];
		String s;
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		DatagramSocket socket = new DatagramSocket(1234);

		try
		{
			socket.setSoTimeout(10000);
			
			for (int i = 0; i < 10; i++)
			{
				try
				{
					socket.receive(packet);
					s = new String(buffer, 0, packet.getLength());
					
					s = descobrirId(s);
					
					System.out.println("From: " + packet.getAddress().getHostName() + ":" + packet.getPort() + ":" + s);
					
					enviarConfirmacao(buffer, packet, socket);
				}
				catch (InterruptedIOException e)
				{
					System.out.println("TimeOut: " + e);					
				}
				
				// Podemos fazer algo antes de tentar de novo
			}
		}
		catch (SocketException e)
		{
			System.out.println("Erro de Socket: " + e);
		}
	}
	
	public static String descobrirId(String s)
	{
		String msg[] = s.split("");		
		s = ""; //limpo o valor antigo da variavel, para reusar ela
		
		String idMensagem = msg[0];
		idMsg = Integer.parseInt(idMensagem);

		for(int i=1 ; i < msg.length ; i++)
			s += msg[i];

		return s;
	}
	
	@SuppressWarnings("resource") public static void enviarConfirmacao(byte[] buffer, DatagramPacket packet, DatagramSocket socket) throws IOException
	{		
		InetAddress end = InetAddress.getByName("192.168.25.5");		
		
		String confirmacao = Integer.toString(idMsg);
		byte[] msg = new byte[confirmacao.length()];
	    msg = confirmacao.getBytes();
	    packet = new DatagramPacket(msg, msg.length, end, 1234);
	   	socket = new DatagramSocket();
	    socket.send(packet);		
	}
}
