package Cliente;

import java.util.Arrays;

public class Desencripta
{

	public static void main(String[] args)
	{
		descriptografar("L8p0S0p0F3p0I1k2p0M1k1i5");
	}

	public static void descriptografar(String stringCriptografada)
	{
		String[] caractere = { "a", "b", "c", "d", "e", "f", "g", "h",
		        "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
		        "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
		        "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
		        "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3",
		        "4", "5", "6", "7", "8", "9", " " };

		String[] codigo = { "p0", "C6", "g2", "I9", "k1", "Q3", "b6",
		        "U4", "n0", "H4", "o7", "M1", "s8", "F3", "u4", "D1", "m8",
		        "t2", "i5", "P4", "j7", "h7", "R2", "l8", "S0", "A4", "J6",
		        "q2", "N6", "d2", "Z7", "f3", "G1", "t2", "E3", "j5", "O2",
		        "c9", "K6", "A2", "r2", "x1", "B5", "L8", "k2", "z8", "Im",
		        "hS", "EK", "aT", "xM", "sU", "Gn", "dP", "jQ", "Hf", "qz",
		        "p2", "Bb", "Oo", "Cc", "w6", "I1" };

		String[] linha = stringCriptografada.split("");
		String stringDescriptografada = "", silaba = "";

		for (int b = 0; b < linha.length; b++)
		{
			if (b % 2 == 1)
			{
				silaba = linha[b - 1] + linha[b]; // pega as letras que formam a silaba
				stringDescriptografada += caractere[Arrays.asList(codigo).indexOf(silaba)];
			}
		}
		System.out.println("Mensagem:" + stringDescriptografada);
	}
}
