package src.jrpg;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloRPG extends JFrame implements ActionListener {
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private JPanel imagePanel;
    private List<JLabel> imageLabels;

    public ModeloRPG() {
        super("MODELO RPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(800, 400));
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        inputTextField = new JTextField(40);
        inputTextField.addActionListener(this);
        mainPanel.add(inputTextField, BorderLayout.SOUTH);

        add(mainPanel);

        imageLabels = new ArrayList<>();

        pack();
        setVisible(true);

        startAnimation();
    }

    public void actionPerformed(ActionEvent e) {
        String command = inputTextField.getText();
        processCommand(command);
        inputTextField.setText("");
    }

    private void processCommand(String command) {
        // Lógica do RPG

        if (command.equalsIgnoreCase("help")) {
            outputTextArea.append("Comandos disponíveis: help, attack, defend, run\n");
        } else if (command.equalsIgnoreCase("attack")) {
            outputTextArea.append("Você ataca o inimigo e causa 10 de dano.\n");
        } else if (command.equalsIgnoreCase("defend")) {
            outputTextArea.append("Você se defende e bloqueia o ataque do inimigo.\n");
        } else if (command.equalsIgnoreCase("run")) {
            outputTextArea.append("Você foge da batalha.\n");
        } else {
            outputTextArea.append("Comando inválido. Digite 'help' para obter ajuda.\n");
        }
    }

    private void startAnimation() {
        Timer timer = new Timer(41, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateImagePositions();
            }
        });
        timer.start();
    }

    private void updateImagePositions() {
        for (JLabel label : imageLabels) {
            int x = label.getX();
            int y = label.getY();
            x += 1; // Exemplo: incrementa a posição x em 1 pixel
            label.setLocation(x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ModeloRPG();
            }
        });
    }
}
