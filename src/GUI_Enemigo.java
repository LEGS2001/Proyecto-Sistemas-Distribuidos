import java.awt.*;
import javax.swing.*;

public class GUI_Enemigo extends JFrame {
    private Celda[][] grid;

    public GUI_Enemigo(String[][] jugador, String nombre) {
        setTitle(nombre);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // crear el layout para el grid
        GridLayout layout = new GridLayout(7, 6);
        setLayout(layout);

        // crear la matriz de Celdas
        grid = new Celda[7][6];
        for (int i = 0; i < jugador.length; i++) {
            for (int j = 0; j < jugador[i].length; j++) {
                grid[i][j] = new Celda();
                grid[i][j].estado = jugador[i][j];
                add(grid[i][j]);
            }
        }
    }

    public void actualizar(String[][] jugador){
        for (int i = 0; i < jugador.length; i++) {
            for (int j = 0; j < jugador[i].length; j++) {
                grid[i][j].estado = jugador[i][j];
            }
        }
        repaint();
    }

    // clase Celda
    private class Celda extends JPanel {
        private String estado;
        private int tamaño_celda = 100;

        public Celda() {
            setPreferredSize(new Dimension(tamaño_celda, tamaño_celda));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (estado.equals("■")) {
                g.setColor(Color.BLACK);
            } else if (estado.equals("X")) {
                g.setColor(Color.RED);
            } else if (estado.equals("O")) {
                g.setColor(Color.GREEN);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
