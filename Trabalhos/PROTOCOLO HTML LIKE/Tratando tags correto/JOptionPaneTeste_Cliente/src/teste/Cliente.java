package teste;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Cliente
{
	//tags do sistema: azul, italico, negrito;
	static String tag = "", texto = "";
		
	public static void main(String[] arg)
	{		
		Socket s = null;
		String linha = "a";
		String[] vetor = null;

		try
		{
			System.out.println("Esperando servidor enviar uma linha...");
			s = new Socket("192.168.25.6", 6789);

			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			linha = in.readUTF(); //lê a primeira vez			
			vetor = linha.split("");
			
			while(!linha.equals("adeus"))
			{
				System.out.println("entrei no while");
				
				descobrir_tag_e_texto(vetor);
				personaliza_saida();	
				
				tag = ""; texto = ""; //limpa a tag dps q empeteca ela
				
				linha = in.readUTF(); //dps fica lendo ate ele dizer adeus
				vetor = linha.split("");
				
				System.out.println(linha);
				System.out.println(Arrays.toString(vetor));
				System.out.println();				
			}		

			out.flush();
			out.writeUTF(texto);	
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
				System.out.println("Conexao encerrada");
			}
			catch (Exception e2)
			{
			}
		}
		try
		{
			System.in.read();
		}
		catch (Exception e3)
		{
		}
	}
	
	public static void descobrir_tag_e_texto(String[] vetor)
	{
		System.out.println("entrei no descobre tag");
		
		// Pegar tag:
		int guardaI = 0;
		for (int i = 1; i < vetor.length; i++)// começa em um, pq o < n importa
		{
			guardaI = i;
			if (vetor[i].equals(">"))
				break;

			tag += vetor[i];
		}
		// pegar texto:
		for (int j = guardaI + 1; j < vetor.length; j++)
		{
			if (vetor[j].equals("<"))
				break;

			texto += vetor[j];
		}
		System.out.println("tag: " + tag + " texto: " + texto);
		
		System.out.println("sai do descobre tag");
	}

	public static void personaliza_saida()
	{
		System.out.println("entrei no altera");
		
		if (tag.equals("azul"))
		{
			// MUDA A FONTE
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("ARIAL", Font.PLAIN, 20)));
			// MUDA A COR
			UIManager.put("OptionPane.messageForeground", Color.blue);			
		}
		
		else if (tag.equals("italico"))
		{
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("ARIAL", Font.ITALIC, 20)));
			UIManager.put("OptionPane.messageForeground", Color.black);
		}
		
		else if (tag.equals("negrito"))
		{
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("ARIAL", Font.BOLD, 20)));
			UIManager.put("OptionPane.messageForeground", Color.black);
		}
		
		JOptionPane.showMessageDialog(null, texto);
		

		System.out.println("sai do altera");
	}
}
