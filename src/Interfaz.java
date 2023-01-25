import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    private Casilla[][] casillas;

    public Interfaz(int[][] jugador) {
        setTitle("Batalla de Barcos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear el layout para el grid
        GridLayout layout = new GridLayout(7, 6);
        setLayout(layout);

        // Crear la matriz de casillas
        casillas = new Casilla[7][6];
        for (int i = 0; i < jugador.length; i++) {
            for (int j = 0; j < jugador[i].length; j++) {
                casillas[i][j] = new Casilla();
                casillas[i][j].estado = jugador[i][j];
                add(casillas[i][j]);
            }
        }
    }

    // Clase Casilla
    private class Casilla extends JPanel {
        private int estado;

        public Casilla() {
            setPreferredSize(new Dimension(70, 70));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 6));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1){ // click izquierdo
                        estado = 1;
                        repaint();
                    } else if (e.getButton() == 3){ // click derecho
                        estado = 0;
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
            } else if (estado == 1) {
                g.setColor(Color.BLACK);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }    
}