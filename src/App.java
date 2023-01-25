public class App {
    public static void main(String[] args) throws Exception {
        Tablero tablero = new Tablero();
        int[][] jugador1 = tablero.crearTablero();
        System.out.println(jugador1[0][0]);

        Interfaz gui = new Interfaz(jugador1);
        gui.setVisible(true);
    }
}
