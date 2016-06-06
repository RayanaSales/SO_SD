package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Cliente //recebe msg, envia confirmacao
{
	public static void main(String[] arg)
	{	
		Socket s = null;
		
		try
		{
			System.out.println("Conectando...");
			s = new Socket("192.168.25.5", 6789);

			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			while(true)
			{
				System.out.println("Esperando mensagem do servidor:");
				receberMensagem(in, out);
			}
			
			//out.flush();
			// out.writeUTF(texto);
		}
		catch (Exception e)
		{
			System.out.println("Erro: " + e.getMessage());
		}
		finally
		{
			try
			{
				if (s != null)
					s.close();
			}
			catch (Exception e2)
			{
			}
		}
		System.out.println("Conexao encerrada");
		try
		{
			System.in.read();
		}
		catch (Exception e3)
		{
		}
	}
	
	public static void receberMensagem(DataInputStream in, DataOutputStream out) throws IOException
	{		
		String mensagemRecebida = in.readUTF();	//recebe msg		
		String msg[] = mensagemRecebida.split("");		
		mensagemRecebida = ""; //limpo o valor antigo da variavel, para reusar ela
		
		String idMensagem = msg[0];
		
		for(int i=1 ; i < msg.length ; i++)
			mensagemRecebida += msg[i];
		
		System.out.println("Mensagem recebida: " + mensagemRecebida);	
		JOptionPane.showConfirmDialog(null, mensagemRecebida);	
		
		enviarConfirmacao(idMensagem, in, out);
	}
	
	public static void enviarConfirmacao(String idMensagem, DataInputStream in, DataOutputStream out)
	{		
		try
		{		
			System.out.println("Enviando confirmacao...");
			out.writeUTF(idMensagem);				
			System.out.println("Confirmacao enviada.\n");			
		}
		catch (Exception e)
		{
			System.out.println("Erro: " + e.getMessage());
		}			
	}
}
