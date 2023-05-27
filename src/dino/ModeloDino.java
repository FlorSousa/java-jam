package src.dino;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;

public class ModeloDino extends JPanel implements KeyListener {
    private Image dinoImage;
    private Image cactus_small;
    private Image cactus_large;
    private Image cactus_small_single;
    private Image cactus_large_single;

    private int dinoX, dinoY;
    private int dinoWidth, dinoHeight;
    private Timer timer;
    private boolean isJumping;
    private boolean isRunning;
    private boolean isDead;
    private boolean changeLeg;
    private final int groundPosiY = 680;
    private int contPulo = 1;
    private Image[] dinoSprites = new Image[4];
    BufferedImage spriteSheet;
    BufferedImage cactus_small_sheet;
    BufferedImage cactus_large_sheet;
    BufferedImage cactus_small_single_sheet;
    BufferedImage cactus_large_single_sheet;
    BufferedImage flying_dino_sheet;
    BufferedImage ground_sheet;
    BufferedImage retry_sheet;
    private Font font;
    private String textoMorte = "APERTE ENTER PARA INICIAR";
    private HashMap<Integer, Runnable> keys;
    public ModeloDino() {
        setPreferredSize(new Dimension(800, 800));
        setFocusable(true);
        addKeyListener(this);
        font = new Font("Trebuchet MS", Font.BOLD, 24);

        loadSprite();
        dinoImage = dinoSprites[3];
        dinoX = 100;
        dinoY = 680;
        dinoWidth = 88;
        dinoHeight = 95;
        isJumping = false;
        isRunning = true;
        isDead = true;
        changeLeg =  true;
        keys = new HashMap<>();
        keys.put(KeyEvent.VK_ENTER,()->this.start());
        keys.put(KeyEvent.VK_SPACE,()->this.jump());

        timer = new Timer(55, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    public void setDinoSprite(int index) {
        dinoImage = dinoSprites[index];
    }

    public void loadSprite() {
        try {
            spriteSheet = ImageIO.read(new File("src/dino/assets/sheet_dino2.png"));
            cactus_small_sheet = ImageIO.read(new File("src/dino/assets/Cactus_Small_Doube.png"));
            cactus_large_sheet = ImageIO.read(new File("src/dino/assets/Cactus_Large_Doube.png"));
            cactus_small_single_sheet = ImageIO.read(new File("src/dino/assets/Cactus_Small_Single.png"));
            cactus_large_single_sheet = ImageIO.read(new File("src/dino/assets/Cactus_Large_Single.png"));
            flying_dino_sheet = ImageIO.read(new File("src/dino/assets/flying_dino.png"));
            ground_sheet = ImageIO.read(new File("src/dino/assets/Ground.png"));
            retry_sheet = ImageIO.read(new File("src/dino/assets/Retry.png"));
            dinoSprites[0] = spriteSheet.getSubimage(295, 54, 88, 95);
            dinoSprites[1] = spriteSheet.getSubimage(392, 54, 88, 95);
            dinoSprites[2] = spriteSheet.getSubimage(199, 54, 88, 95);
            dinoSprites[3] = spriteSheet.getSubimage(102, 54, 88, 95);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.animator();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.drawImage(dinoImage, dinoX, dinoY, this);
        g2d.drawImage(ground_sheet, 0, 680, this);
        if(this.isDead){
            g2d.drawImage(retry_sheet, 360, 360, this);
            g2d.setFont(font);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(this.textoMorte);
            int textHeight = fontMetrics.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2 + fontMetrics.getAscent();
            g2d.drawString(textoMorte, x, y + 80);
        }
        Toolkit.getDefaultToolkit().sync(); // Adicionada esta linha
    }

    public void animator() {
        if (this.isJumping) {
            setDinoSprite(2);
            jumping();
        }
        if (!this.isJumping && !this.isRunning) {
            this.down();
        }
        if (this.isRunning) {
            this.run();
        }

        if (this.isDead){
            setDinoSprite(3);
        }
    }

    public void run() {
        int img = this.changeLeg ? 0:1;
        setDinoSprite(img);
        this.changeLeg = !this.changeLeg;
    }

    public void down() {
        if (this.dinoY <= groundPosiY) {
            this.dinoY += 60;
            this.isJumping = false;
            return;
        }
        this.dinoY = 680;
        this.contPulo = 1;
        this.isRunning = true;
        setDinoSprite(0);
    }

    public void jumping() {
        if (this.dinoY <= 280) {
            this.isJumping = false;
            return;
        }
        this.dinoY -= 50;
    }

    public void kill() {
        this.isDead = true;
    }
    public void start(){
        if (this.isDead) {
            this.isDead = false;
        }
    }

    public void jump(){
        if (!this.isJumping && this.contPulo > 0) {
            this.isJumping = true;
            this.isRunning = false;
            this.contPulo = 0;
        }
    }
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent event){
        keys.get(event.getKeyCode()).run();
    }
   
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MODELO DINO");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            ModeloDino dino = new ModeloDino(); // Crie uma instância da classe
            frame.add(dino);
            frame.pack();
            frame.setVisible(true);
            frame.addKeyListener(dino); // Adicione o KeyListener ao JFrame
        });
    }
}
