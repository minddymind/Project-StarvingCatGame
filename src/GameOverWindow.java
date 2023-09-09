import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GameOverWindow {
    private final JFrame frame;
    ImageIcon failImage;

        public GameOverWindow(){
            frame = new JFrame("Starving Cat Game");
            frame.setSize(710, 410);

            failImage = new ImageIcon(this.getClass().getResource("/images/gameover.png"));
            JLabel label = new JLabel(failImage);
            label.setSize(710, 364);
            JButton exitButton = new JButton("Back to Home");
            exitButton.setContentAreaFilled(true);
            exitButton.setBounds(257, 307, 200, 40);
            exitButton.setBorder(null);
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Rule();
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



