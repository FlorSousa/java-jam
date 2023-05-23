package src.dino;
import java.awt.*;
import java.awt.event.*;

public class ModeloDino extends Frame implements KeyListener {
    private Image dinoImage;
    private int dinoX, dinoY;
    private int dinoWidth, dinoHeight;

    public ModeloDino() {
        setSize(800, 400);
        setTitle("Dinosaur Game");
        setVisible(true);
        addKeyListener(this);

        // Carregar a imagem do dinossauro
        dinoImage = Toolkit.getDefaultToolkit().getImage("src/dino/sheet_dino.png");

        // Definir as coordenadas e o tamanho do recorte do dinossauro na imagem
        dinoX = 100;
        dinoY = 300;
        dinoWidth = 88;   // 94 - 6
        dinoHeight = 90;  // 148 - 58
    }

    public void paint(Graphics g) {
        // Desenhar o recorte do dinossauro na tela
        g.drawImage(dinoImage, dinoX, dinoY, dinoX + dinoWidth, dinoY + dinoHeight, 6, 58, 94, 148, this);
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
        ModeloDino game = new ModeloDino();
    }
}
