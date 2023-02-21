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
		// String message = keyboard.nextLine();

        Tablero tablero = new Tablero();
        int[][] jugador = tablero.crearTablero();

		// imprime el tablero del jugador
		System.out.print("Tablero: ");
		for (int i = 0; i < jugador.length; i++){
			System.out.println();
			for (int j = 0; j < jugador[0].length; j++){
				System.out.print(jugador[i][j] + " ");
			}
		}
		System.out.println();


		String message = "";
        for (int i = 0; i < jugador.length; i++){
            for (int j = 0; j < jugador[i].length; j++){
                message = message + Integer.toString(jugador[i][j]);
            }
        }

		byte[] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020);
		socket.send(packet);
		
		while(true) {
			// recibe el mensaje del servidor
			buffer = new byte[1500];
			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			message = new String(buffer).trim();

			System.out.print("Tablero enemigo: ");
			for (int i = 0; i < jugador.length; i++){
				System.out.println();
				for (int j = 0; j < jugador[0].length; j++){
					System.out.print(message.charAt(j) + " ");
				}
			}
			System.out.println();

			System.out.println("Ingrese su siguiente movimiento: ");


		}
	}
	
	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
