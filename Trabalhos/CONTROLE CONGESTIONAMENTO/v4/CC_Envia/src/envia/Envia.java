package envia;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class Envia // compilado segundo. 
{
	static DatagramPacket packet = null;
	static DatagramSocket socket = null;
	static InetAddress end = null;
	static int idMsg = 0;
	static int tentativasReenvio = 0 ;
		
	public static void main(String[] args) throws Exception
	{		
		while(true)
		{			
			String s2 = "";
			
			try
			{
				String s3 = "a";			
				String s1 = String.valueOf(idMsg);		
				s2 = JOptionPane.showInputDialog("Informe a mensagem:");		
				System.out.println("Mensagem a ser enviada: " + s2);
				s3 = s1 + s2;
				enviarMsg(s3);
				idMsg++;
				
				socket.close();			
			}
			catch(BindException be)
			{
				System.out.println("Erro no bind: " + be);	
				tentativasReenvio++;
				System.out.println("Tentativa de renvio numero: " + tentativasReenvio);
				enviarMsg(s2);					
			}
			catch(Exception e)
			{
				System.out.println("Erro na main: " + e);
			}
		}
	}
	
	public static void enviarMsg(String s) throws IOException
	{		
		System.out.println("Entrei no envia dps q caiu");
		try
		{
			end = InetAddress.getByName("192.168.25.6");
			
			byte[] msg = new byte[s.length()];
			msg = s.getBytes();
			packet = new DatagramPacket(msg, msg.length, end, 1234);
			
			if(socket == null)//nao coloque outro socket na msm porta (ou fecha, ou abre de novo)
				socket = new DatagramSocket();
			
			//socket.setReuseAddress(true);
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
		
		socket.setSoTimeout(10000);
		System.out.println("Vou esperar confirmacao e meus dados estão assim: confirmacao: " + confirmacao + " idRecebido: " + idRecebido);
		try
		{
			System.out.println("Aguardando confirmacao...");
			socket.receive(packet);
			System.out.println("Recebi confirmacao!");
			confirmacao = new String(buffer, 0, packet.getLength()); //confirmacao eh alterada aq
			idRecebido = Integer.parseInt(confirmacao);
			
			System.out.println("Meu dados agora estão assim: confirmacao: " + confirmacao + " idRecebido: " + idRecebido);
		}
		catch (InterruptedIOException e)
		{
			System.out.println("Ja esperei o tempo determinado e não recebi nada.");
			System.out.println("O TimeOut estourou: " + e);
		}
		
		if (confirmacao.equals("no") || idRecebido != idMsg)
		{
			System.out.println("O pacote NAO foi entregue com sucesso. Reenviando...\n");
			tentativasReenvio++;
			System.out.println("Tentativa de renvio numero: " + tentativasReenvio);			
			enviarMsg(s); // reenvia	
		}
		else if (!confirmacao.equals("no") && idRecebido == idMsg)
			System.out.println("O pacote foi entregue com sucesso.\n");
	}
}
