package envia;

import java.io.*;
import java.net.*;

public class EnviaUDP {
	public static void main(String[] args) throws Exception {
		InetAddress end = InetAddress.getByName("192.168.98.66");
		String s = "Oieeee lala";
		byte[] msg = new byte[s.length()];
		msg = s.getBytes();
		DatagramPacket packet = new DatagramPacket(msg, msg.length, end, 1234); //cria um pacote que vai usar
		DatagramSocket socket = new DatagramSocket();//cria um socket
		socket.send(packet);
		socket.close();
	}
}
