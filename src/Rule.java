import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Rule {
    private final JFrame frame;

    public Rule() {
        frame = new JFrame("Rules of Game : Select Map");
        frame.setSize(710, 410);

        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/rules.png"));
        JLabel label = new JLabel(img);
        label.setSize(710, 410);

        JButton easy = new JButton("Savanna");
        easy.setContentAreaFilled(true);
        easy.setBackground(new Color(0, 153, 76)); // Set the background color to a green color
        easy.setBounds(50, 270, 100, 40);
        easy.setBorder(null);
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CatWindow();
                frame.dispose();
            }
        });

        JButton medium = new JButton("Desert");
        medium.setContentAreaFilled(true);
        medium.setBackground(new Color(255, 204, 0)); // Set the background color to a yellow color
        medium.setBounds(300, 270, 100, 40);
        medium.setBorder(null);
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CatWindow2();
                frame.dispose();
            }
        });

        JButton hard = new JButton("Tundra");
        hard.setContentAreaFilled(true);
        hard.setBackground(new Color(0, 102, 204)); // Set the background color to a blue color
        hard.setBounds(550, 270, 100, 40);
        hard.setBorder(null);
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CatWindow3();
                frame.dispose();
            }
        });

        JButton exit = new JButton("Back to Home");
        exit.setContentAreaFilled(true);
        exit.setBackground(new Color(204, 51, 51)); // Set the background color to a red color
        exit.setBounds(250, 317, 200, 40);
        exit.setBorder(null);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StartGame();
                frame.dispose();
            }
        });


        label.add(easy);
        label.add(hard);
        label.add(medium);
        label.add(exit);
        frame.add(label);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // set frame is center your computer
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
