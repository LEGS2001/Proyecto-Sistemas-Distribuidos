import java.util.Random;

public class Tablero {

    // HACER QUE EL TABLERO SE CREE COMO CONSTRUCTOR
    public int[][] crearTablero(){

        final int FILAS = 7;
        final int COLUMNAS = 6;


        int[][] tablero = new int[FILAS][COLUMNAS];
        
        int barcos_pequeños = 0;
        int barcos_grandes = 0;
        int num_barco = 1;

        Random random = new Random();

        while (barcos_pequeños < 3){

            // selecciona una posicion aleatoria del tablero para ubicar un barco
            int fila = random.nextInt(FILAS - 1); // el -1 está para que no pueda ubicar un barco fuera del tablero
            int col = random.nextInt(COLUMNAS - 1); // el -1 está para que no pueda ubicar un barco fuera del tablero
            
            // decide si el barco va a ser horizontal (0) o vertical (1)
            int orientacion = random.nextInt(2);

            if (orientacion == 0){ // el barco está orientado horizontalmente
                if (tablero[fila][col] == 0 && tablero[fila][col + 1] == 0){ // revisa que no exista ningún barco en las posiciones
                    tablero[fila][col] = num_barco;
                    tablero[fila][col + 1] = num_barco;
                    barcos_pequeños++;
                    num_barco++;
                } 
            } else { // el barco está ubicado verticalmente
                if (tablero[fila][col] == 0 && tablero[fila + 1][col] == 0){ // revisa que no exista ningún barco en las posiciones
                    tablero[fila][col] = num_barco;
                    tablero[fila + 1][col] = num_barco;
                    barcos_pequeños++;
                    num_barco++;
                } 
            }
        }

        while (barcos_grandes < 2){

            // selecciona una posicion aleatoria del tablero para ubicar un barco
            int fila = random.nextInt(FILAS - 2); // el -2 está para que no pueda ubicar un barco fuera del tablero
            int col = random.nextInt(COLUMNAS - 2); // el -2 está para que no pueda ubicar un barco fuera del tablero
            
            // decide si el barco va a ser horizontal (0) o vertical (1)
            int orientacion = random.nextInt(2);

            if (orientacion == 0){ // el barco está orientado horizontalmente
                if (tablero[fila][col] == 0 && tablero[fila][col + 1] == 0 && tablero[fila][col + 2] == 0){ // revisa que no exista ningún barco en las posiciones
                    tablero[fila][col] = num_barco;
                    tablero[fila][col + 1] = num_barco;
                    tablero[fila][col + 2] = num_barco;
                    barcos_grandes++;
                    num_barco++;
                } 
            } else { // el barco está ubicado verticalmente
                if (tablero[fila][col] == 0 && tablero[fila + 1][col] == 0 && tablero[fila + 2][col] == 0){ // revisa que no exista ningún barco en las posiciones
                    tablero[fila][col] = num_barco;
                    tablero[fila + 1][col] = num_barco;
                    tablero[fila + 2][col] = num_barco;
                    barcos_grandes++;
                    num_barco++;
                } 
            }
        }

        return tablero;
    }
}
