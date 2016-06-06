import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Servidor {

	public static void main(String[] args) {
		ServerSocket s;
		try {
			s = new ServerSocket(6789);
			System.out.println("Servidor iniciado na porta 6789");
			while (true) {
				Socket cliente = s.accept();
				System.out.println("Conexao estabelecida " + "(" + cliente + ")");

				DataInputStream in = new DataInputStream(cliente.getInputStream());
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

				String codigo = "";

				while (!codigo.equals("adeus")) {
					codigo = JOptionPane.showInputDialog("Informe o código:");
					out.writeUTF(codigo);
				}
				cliente.close();
				System.out.println("Conexao encerrada.");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}

