import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

public class Client {
	public Client() throws Exception{
		DatagramSocket socket = new DatagramSocket();
		Scanner teclado = new Scanner(System.in);

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

		// convierte el tablero en una cadena de caracteres para poderlo enviar al servidor
		String message = "";
        for (int i = 0; i < jugador.length; i++){
            for (int j = 0; j < jugador[i].length; j++){
                message = message + Integer.toString(jugador[i][j]);
            }
        }

		byte[] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020);
		socket.send(packet);
		
		boolean juego_iniciado = false;

		while(true) {
			if (!juego_iniciado){
				// recibe el mensaje del servidor
				buffer = new byte[1500];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				message = new String(buffer).trim();

				// una vez se conectaron los dos jugadores con el cliente, muestra los tableros en pantalla
				System.out.print("Tablero enemigo: ");
				for (int i = 0; i < jugador.length; i++){
					System.out.println();
					for (int j = 0; j < jugador[0].length; j++){
						System.out.print(message.charAt(j) + " ");
					}
				}
				System.out.println();
				juego_iniciado = true;
			}

			// pide el movimiento por teclado (Ejemplo -> A1, C6) para enviarlo al servidor
			System.out.println("Ingrese su siguiente movimiento (Desde A1 -> F7): ");
			message = teclado.nextLine();
			buffer = message.getBytes();
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020);
			socket.send(packet);

			// recibe el movimiento que realizo el jugador enemigo
			buffer = new byte[1500];
			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			message = new String(buffer).trim();
			String fila = Character.toString(message.charAt(1)); // fila empezando desde 0
			String col = Character.toString(message.charAt(0)); // columna empezando desde 0

			// comprueba si un barco ha sido golpeado o no
			if (jugador[Integer.parseInt(fila)][Integer.parseInt(col)] == 0 || jugador[Integer.parseInt(fila)][Integer.parseInt(col)] == 9){
				System.out.println("Tu barco no ha sido golpeado");
				jugador[Integer.parseInt(fila)][Integer.parseInt(col)] = 9;
			} else {
				System.out.println("Uno de tus barcos ha sido golpeado");
				jugador[Integer.parseInt(fila)][Integer.parseInt(col)] = 9;
			}

			// reimprime el tablero con un 9 en los las posiciones que han sido golpeadas
			for (int i = 0; i < jugador.length; i++){
				System.out.println();
				for (int j = 0; j < jugador[0].length; j++){
					System.out.print(jugador[i][j] + " ");
				}
			}
			System.out.println();
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
