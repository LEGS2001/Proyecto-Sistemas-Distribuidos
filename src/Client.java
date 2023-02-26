import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
	public Client() throws Exception{
		DatagramSocket socket = new DatagramSocket();
		Scanner teclado = new Scanner(System.in);

        Tablero tablero = new Tablero();
        int[][] jugador = tablero.crearTablero();

		GUI gui = new GUI(jugador, "Jugador");
        gui.setVisible(true);

		int puntaje = 0;
		int golpes_dados = 0;
		int golpes_recibidos = 0;

		// crea el tablero enemigo con el mensaje obtenido del servidor
		int[][] jugador_enemigo = new int[7][6];

		String[][] enemigo = new String[7][6];
		for (int i = 0; i < enemigo.length; i++){
			for (int j = 0; j < enemigo[0].length; j++){
				enemigo[i][j] = "■";
			}
		}

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

				// una vez se conectaron los dos jugadores con el cliente, crea el tablero enemigo para detectar si ha golpeado cada turno
				int letra = 0;
				for (int i = 0; i < jugador.length; i++){
					for (int j = 0; j < jugador[0].length; j++){
						jugador_enemigo[i][j] =	Character.getNumericValue(message.charAt(letra));
						letra++;
					}
				}

				// System.out.print("Tablero enemigo: ");
				// for (int i = 0; i < jugador_enemigo.length; i++){
				// 	System.out.println();
				// 	for (int j = 0; j < jugador_enemigo[0].length; j++){
				// 		System.out.print(jugador_enemigo[i][j] + " ");
				// 	}
				// }
				// System.out.println();

				juego_iniciado = true;
			}

			// pide el movimiento por teclado (Ejemplo -> C6) para enviarlo al servidor
			// INTENTAR IMPLEMENTAR QUE SEAN 5 ATAQUES POR RONDA 
			// posiblemente enviar un mensaje de 10 caracteres, cada 2 equivalente a una posicion
			// de ahi en el servidor leer cada dos caracteres
			System.out.println("Ingrese su siguiente movimiento (Desde A1 -> F7): ");
			message = teclado.nextLine();
			System.out.println("==================");

			int fila_ataque = Character.getNumericValue(message.charAt(0)) - 10;
			int col_ataque = Character.getNumericValue(message.charAt(1) - 1);
			enemigo[col_ataque][fila_ataque] = "X";

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

			// comprueba si se ha golpeado un barco enemigo
			if (jugador_enemigo[(col_ataque)][(fila_ataque)] == 0 || jugador_enemigo[(col_ataque)][(fila_ataque)] == 9){
				System.out.println("Has fallado");
				jugador_enemigo[(col_ataque)][(fila_ataque)] = 9;
				puntaje = puntaje - 10;
			} else {
				System.out.println("Has golpeado un barco enemigo!");
				jugador_enemigo[(col_ataque)][(fila_ataque)] = 9;
				puntaje = puntaje + 10;
				golpes_dados++;
			}

			// comprueba si un barco ha sido golpeado o no
			if (jugador[Integer.parseInt(fila)][Integer.parseInt(col)] == 0 || jugador[Integer.parseInt(fila)][Integer.parseInt(col)] == 9){
				System.out.println("Tu barco no ha sido golpeado");
				jugador[Integer.parseInt(fila)][Integer.parseInt(col)] = 9;
			} else {
				System.out.println("Uno de tus barcos ha sido golpeado");
				jugador[Integer.parseInt(fila)][Integer.parseInt(col)] = 9;
				golpes_recibidos++;
			}

			// reimprime el tablero con un 9 en los las posiciones que han sido golpeadas
			System.out.print("Tablero: ");
			for (int i = 0; i < jugador.length; i++){
				System.out.println();
				for (int j = 0; j < jugador[0].length; j++){
					if (jugador[i][j] == 9) {
						System.out.print("X" + " ");
					} else {
						System.out.print(jugador[i][j] + " ");
					}
				}
			}
			System.out.println();

			// imprime las casillas golpeadas del tablero enemigo
			System.out.print("Tablero enemigo");
			for (int i = 0; i < enemigo.length; i++){
				System.out.println();
				for (int j = 0; j < enemigo[0].length; j++){
					System.out.print(enemigo[i][j] + " ");
				}
			}
			System.out.println();

			System.out.println("TU PUNTAJE ACTUAL ES: " + puntaje);

			if (golpes_dados == 12){
				System.out.println("Has ganado!!!");
				break;
			}
			if (golpes_recibidos == 12){
				System.out.println("Has perdido!!!");
				break;
			}


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
