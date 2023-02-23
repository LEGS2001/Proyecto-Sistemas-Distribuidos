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
		Boolean juego_iniciado = false;
			
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

			// ya se conectaron los dos jugadores y se puede jugar
			if (juego_iniciado){
				int fila = Character.getNumericValue(message.charAt(0)) - 10;
				int col = Character.getNumericValue(message.charAt(1) - 1);
				System.out.println("Golpearas la casilla " + fila + "," + col);

				String movimiento = Integer.toString(fila) + Integer.toString(col);
				buffer = movimiento.getBytes();
				
				// se recibe el mensaje de cada jugador con su movimiento, y se lo envia al jugador contrario
				if (senders_address.equals(direcciones.get(0)) && senders_port == puertos.get(0)){
					System.out.println("Jugador 1 disparó");
					packet = new DatagramPacket(buffer, buffer.length, direcciones.get(1), puertos.get(1));
					socket.send(packet);
				}
				if (senders_address.equals(direcciones.get(1)) && senders_port == puertos.get(1)){
					System.out.println("Jugador 2 disparó");
					packet = new DatagramPacket(buffer, buffer.length, direcciones.get(0), puertos.get(0));
					socket.send(packet);

				}
			}
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
				juego_iniciado = true;
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
