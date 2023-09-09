import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
