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

		//turno del jugador actual
		int turno = 0;
			
		while(true) {
			
			byte[] buffer = new byte[1500]; // MTU = 1500
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			socket.receive(packet); // retreiving client's message

			InetAddress senders_address = packet.getAddress();
			int senders_port = packet.getPort();

			direcciones.add(senders_address);
			puertos.add(senders_port);

			String message = new String(buffer).trim();
			System.out.println("Received: " + message);

			System.out.println(jugadores.size());

			System.out.println(direcciones.size());

			// si hay menos de dos jugadores, añade los tableros a la lista
			if (jugadores.size() < 2){
				jugadores.add(message);
			}
			// cuando hay dos jugadores conectados, le envia el tablero del enemigo a cada uno de ellos
			if (direcciones.size() == 2){
				message = jugadores.get(1);
				buffer = message.getBytes();
				packet = new DatagramPacket(buffer, buffer.length, direcciones.get(0), puertos.get(0));
				socket.send(packet);

				message = jugadores.get(0);
				buffer = message.getBytes();
				packet = new DatagramPacket(buffer, buffer.length, direcciones.get(1), puertos.get(1));
				socket.send(packet);
			}

			// ya se conectaron los dos jugadores y se puede jugar
			if (jugadores.size() > 2){
				if (turno == 0){
					System.out.println("Turno finalizado");
				} else {
					System.out.println("Turno finalizado");
				}
			}
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
