import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] arg) {
		String texto = " ";
		int c;
		// total = (int)(10*Math.random());
		Socket s = null;
		Scanner scanner = new Scanner(System.in);
		// Devemos criar um HTTP link, vamos receber linha por linha na entrada,
		// ai vamos definir as nossas proprias tags.
		// O cliente insere : <negrito>Esse texto tem que ser Negrito!
		// </negrito> --> Esse texto tem que aparecer em negrito na tela do
		// cliente.
		// Usar o JOptionPanel

		String[] vetorDeLetras;
		int contador = 0;
		String nomeDaTag = "", textoInformado = "";
		try {
			System.out.println("Conectando...");
			s = new Socket("192.168.96.125", 6789);

			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			for (c = 0; c < 10; c++) {
				texto = in.readUTF();

				vetorDeLetras = texto.split("");

				for (int a = 0; a < vetorDeLetras.length; a++) {

					if (vetorDeLetras[a].equals(">")) {
						// ARMAZENAMOS O NOME DA TAG.
						for (int b = 2; b < a; b++) {
							++contador;
							nomeDaTag += vetorDeLetras[b];
						}
					}
					
					//ARMAZENA O TEXTO DENTRO DA TAG.
					if(contador == 1){
						
						textoInformado += vetorDeLetras[a];
						
						if(vetorDeLetras[a + 1].equals("<") && vetorDeLetras[a + 2].equals("/"))
						break;	
					}
					
					
				}
				System.out.println(textoInformado);
			}

			out.flush();
			// out.writeUTF(texto);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (Exception e2) {
			}
		}
		System.out.println("Conexao encerrada");
		try {
			System.in.read();
		} catch (Exception e3) {
		}
	}
}
