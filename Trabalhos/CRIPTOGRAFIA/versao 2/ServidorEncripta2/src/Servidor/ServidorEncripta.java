package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class ServidorEncripta
{
	@SuppressWarnings("unused") public static void main(String[] args)
	{
		ServerSocket s;
		try
		{
			s = new ServerSocket(6789);
			System.out.println("ServidorEncripta iniciado na porta 6789");
			while (true)
			{
				Socket cliente = s.accept();
				System.out.println("Conexao estabelecida " + "(" + cliente + ")");

				DataInputStream in = new DataInputStream(cliente.getInputStream());
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

				String Mensagem = "";

				while (!Mensagem.equals("adeus"))
				{
					Mensagem = pegarMensagem();
					System.out.println("Menagem encriptada: " + Mensagem);
					out.writeUTF(Mensagem);
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

	public static String pegarMensagem()
	{
		String linhaDescriptografada = JOptionPane.showInputDialog("Informe a mensagem:");
		String linhaCriptografada = encriptar(linhaDescriptografada);
		
		return linhaCriptografada;
	}

	public static String encriptar(String string)
	{
		String[] linha = string.split("");
		String linhaCriptografada = "";

		for (int i = 0; i < linha.length; i++)
			linhaCriptografada += buscarCodigo(linha[i]);

		return linhaCriptografada;
	}

	public static String buscarCodigo(String letra)
	{
		String chave = "";

		String[] caractere = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
		        "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
		        "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
		        "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
		        "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
		        "5", "6", "7", "8", "9", " ", 
		        //novos:
		        "ç", "Ç", "ã", "Ã", "é", ",", ".", "!" };

		String[] codigo = { "p0", "C6", "g2", "I9", "k1", "Q3", "b6", "U4",
		        "n0", "H4", "o7", "M1", "s8", "F3", "u4", "D1", "m8", "t2",
		        "i5", "P4", "j7", "h7", "R2", "l8", "S0", "A4", "J6", "q2",
		        "N6", "d2", "Z7", "f3", "G1", "t2", "E3", "j5", "O2", "c9",
		        "K6", "A2", "r2", "x1", "B5", "L8", "k2", "z8", "Im", "hS",
		        "EK", "aT", "xM", "sU", "Gn", "dP", "jQ", "Hf", "qz", "p2",
		        "Bb", "Oo", "Cc", "w6", "I1",
		        //novos:
		        "pq","tp","x9","Pz","tm","v2","Q9","S1"
		        };

		int posicao = Arrays.asList(caractere).indexOf(letra);
		// System.out.println(caractere[posicao] + " " + codigo[posicao]);
		chave = codigo[posicao];

		return chave;
	}

}
