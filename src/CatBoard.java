import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.URL;

public class CatBoard extends JPanel{
    Timer reDrawTimer;
    ActionListener reDrawAL;

    int[][] map;
    Image[] mapSegments;

    Image fishImage;
    Image cherryImage;

    Cat cat;
    ArrayList<Fish> fish;
    ArrayList<PowerUp> cherry;
    ArrayList<Dogs> dog;
//    ArrayList<Move> move;

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
    CatWindow windowBase;
    URL clickSoundURL;


    public CatBoard(JLabel scoreboard, MapData md, CatWindow cw) {
        this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        md_backup = md;
        windowBase = cw;

        move_x = md.getX();
        move_y = md.getY();
        this.map = md.getMap();

        this.isCustom = md.isCustom();
        this.dogBase = md.getDogBasePosition();

        cat = new Cat(md.getCatPosition().x, md.getCatPosition().y, this);
        addKeyListener(cat);

        fish = new ArrayList<>();
        cherry = new ArrayList<>();
        dog = new ArrayList<>();
//        move = new ArrayList<>();

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

        cherry = md.getPowerUpPositions();

        for (DogData dd : md.getDogData()) { // ทำลูปตามจำนวน md.getDogData() โดย dd จะเป็นสมาชิกแต่ละตัวใน
            // md.getDogData()
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
        setBackground(new Color(249, 235, 199));

        mapSegments = new Image[28];// map
        mapSegments[0] = null;
        for (int ms = 1; ms < 28; ms++) {
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("/maps/block_lightgreen.png"));

            } catch (Exception e) {
            }
        }

        try {
            cherryImage = ImageIO.read(this.getClass().getResource("/images/cherry.png"));
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
        reDrawTimer = new Timer(7, reDrawAL);
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
                    new GameOverWindow();
                    windowBase.dispose();
                    break;
                } else {
                    drawScore = true;
                    scorePower++;

                    if (dogBase != null) {
                        d.dead();
                    } else
                        dogToRemove = d;
                }
            } else {  // condution for win game
                if (score + scorePower >= 201 && score + scorePower < 300) {
                    isWin = true;
                    clickSoundURL = getClass().getResource("/sounds/mixkit-final-level-bonus-2061.wav");
                    SoundPlayer.play(clickSoundURL);
                    d.moveTimer.stop();
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
                SoundPlayer.play(clickSoundURL);  // method in SoundPlayer
                isWin = true;
                cat.moveTimer.stop();
            }
            if (isWin) {
                cat.moveTimer.stop();
                windowBase.dispose();
                new NextMap2();
            }
        }

        PowerUp pwuToEat = null;
        // Check cherry that was eaten by cat
        for (PowerUp pwu : cherry) {
            if (cat.logicalPosition.x == pwu.position.x && cat.logicalPosition.y == pwu.position.y)
                pwuToEat = pwu;
        }
        if (pwuToEat != null) {
            clickSoundURL = getClass().getResource("/sounds/mixkit-sweet-kitty-meow-93.wav");
            SoundPlayer.play(clickSoundURL);
            cherry.remove(pwuToEat);
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
        // Draw cherry(Power up)
        for (PowerUp f : cherry) {
            g.drawImage(cherryImage, 10 + f.position.x * 28, 10 + f.position.y * 28, null);
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
            Integer s = scorePower * 50;
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


