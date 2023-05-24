package src.dino;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class ModeloDino extends JFrame implements KeyListener {
    private Image dinoImage;
    private int dinoX, dinoY;
    private int dinoWidth, dinoHeight;
    private Timer timer;
    private boolean isJumping;
    private final int groundPosiY = 680;
    private int contPulo = 1;
    private Image[] dinoSprites = new Image[3];
    private Image[] things = new Image[3];
    BufferedImage spriteSheet;
    
    public ModeloDino() {
        setSize(800, 800);
        setTitle("MODELO DINO");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this); 
        

        // Carregar a imagem do dinossauro
        //dinoImage = Toolkit.getDefaultToolkit().getImage("src/dino/sheet_dino2.png");
        loadSprite();
        dinoImage = dinoSprites[0];
        // Definir as coordenadas e o tamanho do recorte do dinossauro na imagem
        dinoX = 100;
        dinoY = 680;
        dinoWidth = 88;
        dinoHeight = 95;
        isJumping = false;

        // Configurar o temporizador para chamar o método paint() a cada 16 milissegundos (aproximadamente 60 FPS)
        timer = new Timer(22, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    public void setDinoSprite(int index){
        dinoImage = dinoSprites[index];
    }
    public void loadSprite(){
        try{
            spriteSheet = ImageIO.read(new File("src/dino/sheet_dino2.png"));
            //thingsSpriteSheet = ImageIO.read(new File("src/dino/sheet_dino2.png"));
            dinoSprites[0] = spriteSheet.getSubimage(295, 54, 88, 95);
            dinoSprites[1] = spriteSheet.getSubimage(392, 54, 88, 95);
            dinoSprites[2] = spriteSheet.getSubimage(104, 54, 88, 95);
        }catch(Exception e){
            System.out.println(e.toString());
            
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        // Desenhar o recorte do dinossauro na tela
        this.check();
        g.drawImage(dinoImage, dinoX, dinoY, this);
    }

    public void check(){
        if(this.isJumping){
            setDinoSprite(2);
            jumping();
        }else{
            this.down();
        }
    }

    public void down(){
        if(this.dinoY<groundPosiY){
            this.dinoY += 60;
            this.isJumping = false;
            return;
        }
        this.contPulo = 1;
        setDinoSprite(0);
    }

    public void jumping(){
        if(this.dinoY <= 280){
            this.isJumping = false;
            return;
        }
        this.dinoY -= 35;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        // Verificar se a tecla pressionada é a tecla de espaço
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Fazer o dinossauro pular
            if(!this.isJumping && this.contPulo > 0){
                this.isJumping = true;
                this.contPulo = 0;
            }
        }
    }

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModeloDino();
        });
    }
}
