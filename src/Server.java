import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

	public Server() throws Exception{
		
		DatagramSocket socket = new DatagramSocket(2020);
		System.out.println("El servidor esta online.");

		ArrayList<String> jugadores = new ArrayList<String>();
		ArrayList<InetAddress> direcciones = new ArrayList<InetAddress>();
		ArrayList<Integer> puertos = new ArrayList<Integer>();

		while(true) {
			byte[] buffer = new byte[1500]; // MTU = 1500
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			// cuando hay dos jugadores conectados, le envia el tablero del enemigo a cada uno de ellos
			if (direcciones.size() == 2){
				String message = jugadores.get(1);
				buffer = message.getBytes();
				packet = new DatagramPacket(buffer, buffer.length, direcciones.get(0), puertos.get(0));
				socket.send(packet);

				message = jugadores.get(0);
				buffer = message.getBytes();
				packet = new DatagramPacket(buffer, buffer.length, direcciones.get(1), puertos.get(1));
				socket.send(packet);
			}

			socket.receive(packet); // retreiving client's message

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
			e.printStackTrace();
		}
	}
}
