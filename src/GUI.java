import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private Celda[][] grid;

    public GUI(int[][] jugador, String nombre) {
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

    // clase Celda
    private class Celda extends JPanel {
        private int estado;
        private int estado_temp;
        private int tamaño_celda = 100;

        public Celda() {
            setPreferredSize(new Dimension(tamaño_celda, tamaño_celda));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1){ // click izquierdo // HAY UN BUG QUE AL DAR DOBLE CLICK SE QUEDA PERMANENTE EN ESTADO 6
                        estado_temp = estado;
                        estado = 6;
                        repaint();
                    } else if (e.getButton() == 3 && estado == 6) {
                        estado = estado_temp;
                        repaint();
                    }
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (estado == 0) {
                g.setColor(Color.WHITE);
            }
            else if (estado >= 1 && estado <= 5) {
                g.setColor(Color.BLACK);
            }
            else if (estado == 6) {
                g.setColor(Color.GREEN);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(Integer.toString(estado), tamaño_celda/2, tamaño_celda/2);
        }
    }
}