import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGame {
    private final JFrame frame;

    public static void main(String[] args) {
        new StartGame();
    }

    public StartGame() {
        // Set Frame and image
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/screen.png"));
        JLabel label = new JLabel(img);
        label.setSize(710, 410);

        // Create "START GAME" button
        JButton startButton = new JButton("START GAME");
        startButton.setContentAreaFilled(true);
        startButton.setBounds(300, 250, 100, 40);
        startButton.setBorderPainted(true);
        startButton.setFocusPainted(true);
        startButton.setBorder(null);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Rule();  // Open the Rule window
                frame.dispose();  // Close the current frame
            }
        });

        // Create "EXIT" button
        JButton exitButton = new JButton("EXIT");
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(true);
        exitButton.setFocusPainted(true);
        exitButton.setBounds(300, 300, 100, 40);
        exitButton.setBorder(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application when "EXIT" is clicked
            }
        });

        // Add buttons to the label
        label.add(startButton);
        label.add(exitButton);

        // Set up the frame
        frame = new JFrame("Starving Cat Game");
        frame.add(label);
        frame.setSize(710, 440);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
