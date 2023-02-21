public class App {
    public static void main(String[] args) throws Exception {
        Tablero tablero = new Tablero();
        int[][] jugador1 = tablero.crearTablero();
        int[][] jugador2 = tablero.crearTablero();

        String message = "";
        for (int i = 0; i < jugador1.length; i++){
            for (int j = 0; j < jugador1[i].length; j++){
                System.out.println(jugador1[i][j]);
                message = message + Integer.toString(jugador1[i][j]);
            }
        }

        System.out.println(message);

        //GUI gui = new GUI(jugador1, "Jugador 1");
        //gui.setVisible(true);

        //GUI gui2 = new GUI(jugador2, "Jugador 2");
        //gui2.setVisible(true);
    }
}