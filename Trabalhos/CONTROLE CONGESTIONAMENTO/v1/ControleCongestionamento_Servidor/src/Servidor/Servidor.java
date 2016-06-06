package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Servidor//envia msg, espera confiramacao
{
	static String confirmacao = "erro";
//	static boolean tempoLimiteAtingido = false;
//	static String mensagem2 = ""; static int idMensagem2 = 0; static DataInputStream in2 = null; static DataOutputStream out2 = null;
//	
//	static int segundos = 0;
//	static Timer meuTempo = new Timer();
//	static TimerTask task = new TimerTask()
//	{
//		public void run()	
//		{
//			System.out.println("entrei");
//			
//			segundos++;	
//			
//			if (segundos == 3)//se passaram 3 segundos e n recebi uma resposta
//			{
//				meuTempo.cancel();
//				tempoLimiteAtingido = true;
//				System.out.println("Tempo de espera excedido para entregar a mensagem: " + idMensagem2 + "\n");
//				try
//                {
//	                enviarMensagem(mensagem2, idMensagem2, in2, out2);//reenvia 
//                }
//                catch (IOException e)
//                {	               
//	                e.printStackTrace();
//                } 
//				System.out.println(segundos + " segundo(s)");
//			}
//			System.out.println("sai");
//		}
//	};
//	
//	public static void verificarSeAtingiuTempoLimiteParaResponder()
//	{
//		segundos = 0;
//		tempoLimiteAtingido = false;
//		meuTempo = new Timer();
//		
//		meuTempo.scheduleAtFixedRate(task, 1000, 1000);
//	}
	
	public static void main(String[] args)
	{
		ServerSocket s;
		try
		{
			s = new ServerSocket(6789);
			//s.setSoTimeout(20000);//se n receber nada, em 10 seg, desista;
			System.out.println("Servidor iniciado na porta 6789");
			
			while (true)
			{
				Socket cliente = s.accept();
				System.out.println("Conexao estabelecida " + "(" + cliente + ")");

				DataInputStream in = new DataInputStream(cliente.getInputStream());//recebo do cliente
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());//escrevo pro cliente

				String Mensagem = "";
				int idMensagem = 0;
				
				while (!Mensagem.equals("adeus"))
				{
					Mensagem = String.valueOf(idMensagem); //insere o id da mensagem
					Mensagem += JOptionPane.showInputDialog("Informe a mensagem:");
					enviarMensagem(Mensagem, idMensagem, in, out);
					idMensagem++;
				}
				cliente.close();
				System.out.println("Conexao encerrada.");
			}
		}
		catch (Exception e)
		{
			System.out.println("Erro: " + e.getMessage());
		}
	}	
	
	public static void enviarMensagem(String mensagem, int idMensagem, DataInputStream in ,DataOutputStream out) throws IOException
	{
		out.writeUTF(mensagem);
		receberConfirmacao(mensagem, idMensagem, in, out);
	}
	
	public static void receberConfirmacao(String mensagem, int idMensagem, DataInputStream in , DataOutputStream out)
	{	
		try
		{
			System.out.println("Esperando confirmacao do cliente...");
			
			//mensagem2 = mensagem; idMensagem2 = idMensagem; in2 = in; out2 = out;
			//verificarSeAtingiuTempoLimiteParaResponder();
			
			confirmacao = in.readUTF();//lendo confirmacao
			
			int confirmacaoInt = Integer.parseInt(confirmacao);			
			if(confirmacaoInt == idMensagem)						
				System.out.println("Confirmação recebida. A mensagem foi entregue com sucesso.\n");			
			else if (confirmacaoInt != idMensagem) 
			{
				System.out.println("Falha ao entregar mensagem a mensagem: " + idMensagem + "\n");
				enviarMensagem(mensagem, idMensagem, in, out); //reenvia 
			}
			
			out.flush();
			// out.writeUTF(texto);
		}
		catch (Exception e)
		{
			System.out.println("Erro: " + e.getMessage());
		}	
	}	
}
