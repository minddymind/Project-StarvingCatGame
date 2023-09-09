import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartGame implements KeyListener {
    private final JFrame frame;

    public static void main(String[] args) {
        new StartGame();
    }

    public StartGame() {
        // set Frame show in computer
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/screen.png"));
        JLabel label = new JLabel(img);
        label.setSize(710, 410);
        JButton startButton = new JButton("START GAME");
        startButton.setContentAreaFilled(true);
        startButton.setBounds(300, 250, 100, 40);
        startButton.setBorderPainted(true);
        startButton.setFocusPainted(true);
        startButton.setBorder(null);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Rule();
                frame.dispose();
            }
        });

        JButton exitButton = new JButton("EXIT");
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(true);
        exitButton.setFocusPainted(true);
        exitButton.setBounds(300, 300, 100, 40);
        exitButton.setBorder(null);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keyTyped(null);
            }
        });

        label.add(startButton);
        label.add(exitButton);

        frame = new JFrame("Game Over");
        frame.add(label);
        frame.setSize(710, 440);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.exit(0);
    }
}

