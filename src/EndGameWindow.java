import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EndGameWindow {
        private JFrame frame;
        private JButton exitButton;
        private JLabel label;
        ImageIcon vicImage;
        ImageIcon failImage;
        public void win() {
            frame = new JFrame("Victory");
            frame.setSize(710, 410);

            vicImage = new ImageIcon(this.getClass().getResource("/images/victory.png"));
            label = new JLabel(vicImage);
            label.setSize(710, 400);

            exitButton = new JButton("Back to Home");
            exitButton.setContentAreaFilled(true);
            exitButton.setBounds(250, 300, 200, 40);
            exitButton.setBorder(null);
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new StartGame();
                    frame.dispose();
                }
            });

            label.add(exitButton);
            frame.add(label);
            frame.getContentPane().setBackground(Color.PINK);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // set frame is center your computer
            frame.setLayout(null);
            frame.setVisible(true);
        }

        public void lose() {
            frame = new JFrame("Game Over");
            frame.setSize(710, 410);

            failImage = new ImageIcon(this.getClass().getResource("/images/gameover.png"));
            label = new JLabel(failImage);
            label.setSize(710, 364);

            exitButton = new JButton("Back to Home");
            exitButton.setContentAreaFilled(true);
            exitButton.setBounds(250, 300, 200, 40);
            exitButton.setBorder(null);
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new StartGame();
                    frame.dispose();
                }
            });

            label.add(exitButton);
            frame.add(label);
            frame.getContentPane().setBackground(new Color(35, 35, 35));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // set frame is center your computer
            frame.setLayout(null);
            frame.setVisible(true);
        }
}
