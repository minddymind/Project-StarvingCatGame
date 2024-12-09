import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class NextMap2 {
    private final JFrame frame;
    ImageIcon vicImage;

    public NextMap2() {
        frame = new JFrame("Victory");
        frame.setSize(710, 410);
        frame.getContentPane().setBackground(Color.PINK); // Set background color for the frame

        vicImage = new ImageIcon(this.getClass().getResource("/images/victory.png"));
        JLabel label = new JLabel(vicImage);
        label.setSize(710, 370);

        // "Next Map" Button
        JButton startButton = new JButton("Next Map");
        startButton.setContentAreaFilled(true);
        startButton.setBackground(new Color(0, 153, 76)); // Set background color (green)
        startButton.setBounds(550, 310, 100, 40);
        startButton.setBorder(null);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CatWindow2(); // Open the next map window
                frame.dispose();  // Close current window
            }
        });

        // "Back to Home" Button
        JButton exitButton = new JButton("Back to Home");
        exitButton.setContentAreaFilled(true);
        exitButton.setBackground(new Color(204, 51, 51)); // Set background color (red)
        exitButton.setBounds(50, 310, 100, 40);
        exitButton.setBorder(null);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Rule(); // Go back to the rules window
                frame.dispose();  // Close current window
            }
        });

        // Add buttons to the label
        label.add(startButton);
        label.add(exitButton);
        frame.add(label);

        // Window settings
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window on screen
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
