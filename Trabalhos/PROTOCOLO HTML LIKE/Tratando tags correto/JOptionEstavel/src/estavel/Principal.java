	
package estavel;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Principal
{
	//tags do sistema: azul, italico, negrito;
	static String tag = "", texto = "";

	public static void main(String[] args)
	{
		String linha = "<azul>Natalia</azul>";
		String[] vetor = linha.split("");

		System.out.println(linha);
		System.out.println(Arrays.toString(vetor));

		descobrir_tag_e_texto(vetor);
		personaliza_saida();
	}

	public static void descobrir_tag_e_texto(String[] vetor)
	{
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
	}

	public static void personaliza_saida()
	{
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
	}
}