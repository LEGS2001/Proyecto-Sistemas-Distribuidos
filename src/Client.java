import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

	final int FILAS = 7;
	final int COLUMNAS = 6;

	public Client() throws Exception{
		DatagramSocket socket = new DatagramSocket();
		// Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter your message: ");
		// String message = keyboard.nextLine();

        Tablero tablero = new Tablero();
        int[][] jugador = tablero.crearTablero();
		String message = "";
        for (int i = 0; i < jugador.length; i++){
            for (int j = 0; j < jugador[i].length; j++){
                message = message + Integer.toString(jugador[i][j]);
            }
        }

		byte[] buffer = message.getBytes();

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020);

		socket.send(packet);
		System.out.println("Tablero: " + jugador);
		
		while(true) {
			// Recibe el mensaje del servidor
			buffer = new byte[1500];
			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			message = new String(buffer).trim();
		}
	}
	
	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
