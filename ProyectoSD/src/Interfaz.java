import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    private Casilla[][] casillas;

    public Interfaz() {
        setTitle("Batalla de Barcos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear el layout para el grid
        GridLayout layout = new GridLayout(7, 6);
        setLayout(layout);

        // Crear la matriz de casillas
        casillas = new Casilla[7][6];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                casillas[i][j] = new Casilla();
                add(casillas[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        Interfaz gui = new Interfaz();
        gui.setVisible(true);
    }

    // Clase Casilla
    private class Casilla extends JPanel {
        private int estado;

        public Casilla() {
            setPreferredSize(new Dimension(70, 70));
            // setPreferredSize(new Dimension(500/7, 500/6));
            estado = 0;
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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
                g.setColor(Color.RED);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    
    
    
    
}