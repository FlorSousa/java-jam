package src.dino;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModeloDino extends JFrame implements KeyListener {
    private Image dinoImage;
    private int dinoX, dinoY;
    private int dinoWidth, dinoHeight;

    public ModeloDino() {
        setSize(800, 400);
        setTitle("MODELO DINO");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        // Carregar a imagem do dinossauro
        dinoImage = Toolkit.getDefaultToolkit().getImage("src/dino/sheet_dino2.png");

        // Definir as coordenadas e o tamanho do recorte do dinossauro na imagem
        dinoX = 100;
        dinoY = 300;
        dinoWidth = 88;
        dinoHeight = 95;
    }

    public void paint(Graphics g) {
        super.paint(g);
        // Desenhar o recorte do dinossauro na tela
        g.drawImage(dinoImage, dinoX, dinoY, dinoX + dinoWidth, dinoY + dinoHeight, 295, 54, 382, 148, this);
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        // Verificar se a tecla pressionada é a tecla de espaço
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Fazer o dinossauro pular
            dinoY -= 50;
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeloDino game = new ModeloDino();
        });
    }
}
