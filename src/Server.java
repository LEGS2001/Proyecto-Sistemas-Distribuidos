import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

	public Server() throws Exception{
		
		DatagramSocket socket = new DatagramSocket(2020);
		System.out.println("Receiver is running.");

		ArrayList<String> jugadores = new ArrayList<String>();
		ArrayList<InetAddress> direcciones = new ArrayList<InetAddress>();
		ArrayList<Integer> puertos = new ArrayList<Integer>();

		while(true) {
			byte[] buffer = new byte[1500]; // MTU = 1500
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			if (jugadores.size() > 2){
				
				for (int i = 0; i < jugadores.size(); i++){
					String message = jugadores.get(i);
					buffer = message.getBytes();
					packet = new DatagramPacket(buffer, buffer.length, direcciones.get(i), puertos.get(i));
					socket.send(packet);
				}
			}


			socket.receive(packet); // retreiving sender's message

			String message = new String(buffer).trim();
			System.out.println("Received: " + message);

			jugadores.add(message);

			InetAddress senders_address = packet.getAddress();
			int senders_port = packet.getPort();

			direcciones.add(senders_address);
			puertos.add(senders_port);
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
