import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverWindow {
    private final JFrame frame;
    private ImageIcon failImage;

    public GameOverWindow() {
        frame = new JFrame("Starving Cat Game");
        frame.setSize(710, 410);
        frame.setLocationRelativeTo(null); // Set window to be center of the screen
        frame.getContentPane().setBackground(new Color(35, 35, 35)); // Set background color of the frame

        failImage = new ImageIcon(this.getClass().getResource("/images/gameover.png"));
        JLabel label = new JLabel(failImage);
        label.setSize(710, 364); // Set label size to fit image size (adjust this if needed)

        // Create "Back to Home" button
        JButton exitButton = new JButton("Back to Home");
        exitButton.setContentAreaFilled(true);
        exitButton.setBackground(new Color(204, 51, 51)); // Set background color for the button (red)
        exitButton.setBounds(257, 307, 200, 40); // Position and size the button
        exitButton.setBorder(null); // Remove border

        // Add action listener to button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Rule(); // Open the Rule window (or the desired window)
                frame.dispose(); // Close the current window
            }
        });

        // Add button to label (the label is the background for the frame)
        label.add(exitButton);

        // Add label (with button) to the frame
        frame.add(label);

        // Set window properties
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null); // Use null layout to manually position components
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameOverWindow(); // Create and show the GameOver window
    }
}
