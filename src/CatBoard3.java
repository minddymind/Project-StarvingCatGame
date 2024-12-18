import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
public class CatBoard3 extends JPanel {
    Timer reDrawTimer;
    ActionListener reDrawAL;

    int[][] map;
    Image[] mapSegments;

    Image fishImage;
    Image strawberryImage;

    Cat cat;
    ArrayList<Fish> fish;
    ArrayList<PowerUp> strawberry;
    ArrayList<Dogs> dog;

    boolean isCustom = false;
    boolean isGameOver = false;
    boolean isWin = false;
    boolean drawScore = false;
    boolean clearScore = false;
    int scorePower = 0;

    int score;
    JLabel scoreboard;

    public Point dogBase;

    public int move_x;
    public int move_y;

    MapData md_backup;
    CatWindow3 windowBase;
    EndGameWindow eg = new EndGameWindow();
    URL clickSoundURL;

    public CatBoard3(JLabel scoreboard, MapData md, CatWindow3 catWindow3) {
        this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        md_backup = md;
        windowBase = catWindow3;

        move_x = md.getX();
        move_y = md.getY();
        this.map = md.getMap();

        this.isCustom = md.isCustom();
        this.dogBase = md.getDogBasePosition();

        cat = new Cat(md.getCatPosition().x, md.getCatPosition().y, this);
        addKeyListener(cat);

        fish = new ArrayList<>();
        strawberry = new ArrayList<>();
        dog = new ArrayList<>();

        if (!isCustom) {
            for (int i = 0; i < move_x; i++) {
                for (int j = 0; j < move_y; j++) {
                    if (map[i][j] == 0)
                        fish.add(new Fish(i, j));
                }
            }
        } else {
            fish = md.getFishPositions();
        }

        strawberry = md.getPowerUpPositions();

        dog = new ArrayList<>();
        for (DogData dd : md.getDogData()) {
            switch (dd.getType()) {
                case Brown:
                    dog.add(new BrownDog(dd.getX(), dd.getY(), this));
                    break;
                case Cream:
                    dog.add(new CreamDog(dd.getX(), dd.getY(), this));
                    break;
                case Gray:
                    dog.add(new GrayDog(dd.getX(), dd.getY(), this));
                    break;
                default:
                    break;
            }
        }

//        move = md.getTeleports();

        setLayout(null);
        setSize(20 * move_x, 20 * move_y);
        setBackground(new Color(151, 207, 255));

        mapSegments = new Image[28]; // map
        mapSegments[0] = null;
        for (int ms = 1; ms < 28; ms++) {
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("/maps/block_snowman.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            strawberryImage = ImageIO.read(this.getClass().getResource("/images/strawberry.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fishImage = ImageIO.read(this.getClass().getResource("/images/fish.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        reDrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
            }
        };
        reDrawTimer = new Timer(1, reDrawAL);
        reDrawTimer.start();

        clickSoundURL = getClass().getResource("/sounds/cat_start.wav");
        SoundPlayer.play(clickSoundURL);
    }

    private void collisionTest() {
        Rectangle pr = new Rectangle(cat.pixelPosition.x + 13, cat.pixelPosition.y + 13, 2, 2);
        Dogs dogToRemove = null;
        for (Dogs d : dog) {
            Rectangle g = new Rectangle(d.pixelPosition.x, d.pixelPosition.y, 28, 28);

            if (pr.intersects(g)) {
                if (!d.isDead()) {
                    clickSoundURL = getClass().getResource("/sounds/cat_lose.wav");
                    SoundPlayer.play(clickSoundURL);
                    cat.moveTimer.stop();
                    cat.animTimer.stop();
                    d.moveTimer.stop();
                    isGameOver = true;
                    windowBase.dispose();
                    eg.lose();
                    break;
                } else {
                    drawScore = true;
                    scorePower++;

                    if (dogBase != null) {
                        d.dead();
                    } else
                        dogToRemove = d;
                }
            } else { // condition for win game
                if (score + scorePower >= 801) {
                    d.moveTimer.stop();
                    isWin = true;
                    clickSoundURL = getClass().getResource("/sounds/mixkit-final-level-bonus-2061.wav");
                    SoundPlayer.play(clickSoundURL);
                    windowBase.dispose();
                    eg.win();
                    break;
                }
            }
            if (dogToRemove != null) {
                dog.remove(dogToRemove);
            }
        }
    }

    private void update() {
        /*
         * this method is update position/die/alive of cat and dogs and remove fish when
         * cat ate
         */
        Fish fishToEat = null;
        for (Fish f : fish) {
            if (cat.logicalPosition.x == f.position.x && cat.logicalPosition.y == f.position.y)
                fishToEat = f;
        }

        if (fishToEat != null) {
            clickSoundURL = getClass().getResource("/sounds/cat_eat.wav");
            SoundPlayer.play(clickSoundURL);
            fish.remove(fishToEat);
            score++;
            scoreboard.setText("    Score : " + score);

            if (fish.size() == 0) {
                clickSoundURL = getClass().getResource("/sounds/cat_intermission.wav");
                SoundPlayer.play(clickSoundURL); // method in SoundPlayer
                isWin = true;
                cat.moveTimer.stop();
            }
            if (isWin) {
                cat.moveTimer.stop();
                windowBase.dispose();
                new EndGameWindow();
            }
        }

        PowerUp pwuToEat = null;
        // Check Strawberry that was eaten by cat
        for (PowerUp pwu : strawberry) {
            if (cat.logicalPosition.x == pwu.position.x && cat.logicalPosition.y == pwu.position.y)
                pwuToEat = pwu;
        }
        if (pwuToEat != null) {
            clickSoundURL = getClass().getResource("/sounds/mixkit-sweet-kitty-meow-93.wav");
            SoundPlayer.play(clickSoundURL);  // sound of cat when it eat strawberry
            strawberry.remove(pwuToEat);
            scorePower = 1;
            drawScore = true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Walls
        for (int i = 0; i < move_x; i++) {
            for (int j = 0; j < move_y; j++) {
                if (map[i][j] > 0) {
                    g.drawImage(mapSegments[map[i][j]], 10 + i * 28, 10 + j * 28, null);
                }
            }
        }
        // Draw Fish
        for (Fish f : fish) {
            g.drawImage(fishImage, 10 + f.position.x * 28, 10 + f.position.y * 28, null);
        }
        // Draw Strawberry(Power up)
        for (PowerUp f : strawberry) {
            g.drawImage(strawberryImage, 10 + f.position.x * 28, 10 + f.position.y * 28, null);
        }
        // Draw Cat
        g.drawImage(cat.getCatImage(), 10 + cat.pixelPosition.x, 10 + cat.pixelPosition.y, null);
        // Draw Dogs
        for (Dogs d : dog) {
            g.drawImage(d.getDogImage(), 10 + d.pixelPosition.x, 10 + d.pixelPosition.y, null);
        }

        if (clearScore) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore = false;
        }

        if (drawScore) {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.yellow);
            Integer s = scorePower * 100;
            g.drawString(s.toString(), cat.pixelPosition.x + 13, cat.pixelPosition.y + 50);
            score += s;
            scoreboard.setText("    Score : " + score);
            clearScore = true;
        }
    }

    @Override
    public void processEvent(AWTEvent ae) {
        if (ae.getID() == Messenger.UPDATE) {
            update();
        } else if (ae.getID() == Messenger.COLTEST) {
            if (!isGameOver) {
                collisionTest();
            }
        } else {
            super.processEvent(ae);
        }
    }
}

