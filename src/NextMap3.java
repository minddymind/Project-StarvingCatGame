import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextMap3 {
    private final JFrame frame;
    ImageIcon vicImage;

    public NextMap3() {
        frame = new JFrame("Victory");
        frame.setSize(710, 410);
        frame.getContentPane().setBackground(Color.PINK);

        vicImage = new ImageIcon(this.getClass().getResource("/images/victory.png"));
        JLabel label = new JLabel(vicImage);
        label.setSize(710, 370);

        // "Next Map" Button
        JButton startButton = new JButton("Next Map");
        startButton.setContentAreaFilled(true);
        startButton.setBackground(new Color(0, 153, 76)); // Set the background color (green)
        startButton.setBounds(550, 310, 100, 40);
        startButton.setBorder(null);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CatWindow3();
                frame.dispose();
            }
        });

        // "Back to Home" Button
        JButton exitButton = new JButton("Back to Home");
        exitButton.setContentAreaFilled(true);
        exitButton.setBackground(new Color(204, 51, 51)); // Set the background color (red)
        exitButton.setBounds(50, 310, 100, 40);
        exitButton.setBorder(null);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StartGame();
                frame.dispose();
            }
        });

        label.add(startButton);
        label.add(exitButton);
        frame.add(label);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // set frame is center your computer
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
