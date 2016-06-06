package envia;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class Envia // compilado segundo. Tento reenviar 3 vzs, se n chegar confirmação (q n chega nunca), desiste. É só uma forma de garantir que vai chegar, e vai parar de lotar o receptor 
{
	static DatagramPacket packet = null;
	static DatagramSocket socket = null;
	static InetAddress end = null;
	static int idMsg = 0;
	static int tentativasReenvio = 0;
	
	public static void main(String[] args) throws Exception
	{
		String s2 = "";
		
		try
		{
			String s3 = "a";			
			String s1 = String.valueOf(idMsg);		
			s2 = JOptionPane.showInputDialog("Informe a mensagem:");		
			System.out.println("Mensagem enviada: "+ s2);
			s3 = s1 + s2;
			enviarMsg(s3);
			idMsg++;
			
			socket.close();			
		}
		catch(BindException be)
		{
			System.out.println("Erro no bind: " + be);
			
			if (tentativasReenvio < 4)
			{
				tentativasReenvio++;
				enviarMsg(s2);			
			}
			else
			{
				System.exit(0);			
			}
		}
		catch(Exception e)
		{
			System.out.println("Erro na main: " + e);
		}
	}
	
	public static void enviarMsg(String s) throws IOException
	{		
		try
		{
			end = InetAddress.getByName("192.168.25.6");
			
			byte[] msg = new byte[s.length()];
			msg = s.getBytes();
			packet = new DatagramPacket(msg, msg.length, end, 1234);
			socket = new DatagramSocket();
			socket.send(packet);		
		}
		catch(BindException be)
		{
			System.out.println("Erro no bind: " + be);
		}
		catch(Exception e)
		{
			System.out.println("Erro durante o envio da mensagem: " + e);
		}
		

		aguardarConfirmacao(s);
	}
	
	public static void aguardarConfirmacao(String s) throws IOException
	{
		byte[] buffer = new byte[1024];
		String confirmacao = "no";
		int idRecebido = 0;
		
		try
		{
			packet = new DatagramPacket(buffer, buffer.length);		
			socket = new DatagramSocket(1234);				
		}
		catch(BindException be)
		{
			System.out.println("Erro no bind: " + be);			
		}

		System.out.println("Aguardando confirmacao...");
		socket.setSoTimeout(10000);

		try
		{
			socket.receive(packet);
			confirmacao = new String(buffer, 0, packet.getLength());
			idRecebido = Integer.parseInt(confirmacao);
			
			System.out.println("Id que veio na confirmação: " + idRecebido);
		}
		catch (InterruptedIOException e)
		{
			System.out.println("TimeOut: " + e);
		}

		System.out.println("confirmacao: " + confirmacao);
		System.out.println("idRecebido: " + idRecebido + "\n" + "idMsg: " + idMsg + "\n");
		if (confirmacao.equals("no") || idRecebido != idMsg)
		{
			System.out.println("O pacote NAO foi entregue com sucesso.\n");
			System.out.println("Reenviando...\n");
			
			if (tentativasReenvio < 4)//se ja tentei reenviar 3 vezes e n pegou, desisto.
			{
				tentativasReenvio++;
				enviarMsg(s);		// reenvia	
			}
			else System.exit(0);
		}
		else if (!confirmacao.equals("no") && idRecebido == idMsg)
			System.out.println("O pacote foi entregue com sucesso.\n");
	}
}
