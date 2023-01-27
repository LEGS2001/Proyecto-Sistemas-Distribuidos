public class App {
    public static void main(String[] args) throws Exception {
        Tablero tablero = new Tablero();
        int[][] jugador1 = tablero.crearTablero();
        int[][] jugador2 = tablero.crearTablero();

        GUI gui = new GUI(jugador1);
        gui.setVisible(true);

        GUI gui2 = new GUI(jugador2);
        gui2.setVisible(true);
    }
}
