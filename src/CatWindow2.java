import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
public class CatWindow2 extends JFrame{

    public CatWindow2() {
        setTitle("Starving Cat Game : Desert Map");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);

        setSize(710, 410);
        setVisible(true);
        setLocationRelativeTo(null);

        JLabel scBoard = new JLabel("  Score : 0");
        scBoard.setForeground(new Color(16, 16, 16));

        MapData mapA = getMapFromResource("/maps/mapdesert.txt");
        adjustMap(mapA);

        CatBoard2 cb2 = new CatBoard2(scBoard, mapA, this);
        addKeyListener(cb2.cat);

        this.getContentPane().add(scBoard, BorderLayout.SOUTH);
        this.getContentPane().add(cb2);
        setVisible(true);
    }

    public int[][] loadMap(int mx, int my, String mapA) {
        try {
            Scanner sc = new Scanner(this.getClass().getResourceAsStream(mapA));
            int[][] map = new int[mx][my];
            for (int y = 0; y < my; y++) {
                for (int x = 0; x < mx; x++) {
                    map[x][y] = sc.nextInt();
                }
            }
            sc.close();
            return map;
        } catch (Exception e) {
            System.err.println("Error Reading Map File !");
            return null;
        }
    }

    public MapData getMapFromResource(String mapA) {
        String mapStr = "";
        try {
            Scanner sc = new Scanner(this.getClass().getResourceAsStream(mapA));
            StringBuilder sb = new StringBuilder();
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                sb.append(line).append('\n');
            }
            mapStr = sb.toString();
        } catch (Exception e) {
            System.err.println("Error Reading Map File !");
        }
        if ("".equals(mapStr)) {
            System.err.println("Map is Empty !");
        }
        return MapEditor.compileMap(mapStr); // error บรรทัดนี้

    }

    // Dynamically Generate Map Sagment
    public void adjustMap(MapData mapA) {
        int[][] map = mapA.getMap();
        int mx = mapA.getX();
        int my = mapA.getY();

        for (int y = 0; y < my; y++) {
            for (int x = 0; x < mx; x++) {
                boolean l, r, t, b, tl, tr, bl, br;
                l = false;
                r = false;
                t = false;
                b = false;
                tl = false;
                tr = false;
                bl = false;
                br = false;

                if (map[x][y] > 0 && map[x][y] < 26) {
                    int set = 0;
                    // left
                    if (x > 0 && map[x - 1][y] > 0 && map[x - 1][y] < 26)
                        l = true;
                    // right
                    if (x < mx - 1 && map[x + 1][y] > 0 && map[x + 1][y] < 26)
                        r = true;
                    // top
                    if (y > 0 && map[x][y - 1] > 0 && map[x][y - 1] < 26)
                        t = true;
                    // bottom
                    if (y < my - 1 && map[x][y + 1] > 0 && map[x][y + 1] < 26)
                        b = true;
                    // Top left
                    if (x > 0 && y > 0 && map[x - 1][y - 1] > 0 && map[x - 1][y - 1] < 26)
                        tl = true;
                    // Top right
                    if (x < mx - 1 && y > 0 && map[x + 1][y - 1] > 0 && map[x + 1][y - 1] < 26)
                        tr = true;
                    // Bottom Left
                    if (x > 0 && y < my - 1 && map[x - 1][y + 1] > 0 && map[x - 1][y + 1] < 26)
                        bl = true;
                    // Bottom right
                    if (x < mx - 1 && y < my - 1 && map[x + 1][y + 1] > 0 && map[x + 1][y + 1] < 26)
                        br = true;
                    // Decide Image to View
                    if (!r && !l && !t && !b)
                        set = 23;
                    if (r && !l && !t && !b)
                        set = 22;
                    if (!r && l && !t && !b)
                        set = 25;
                    if (!r && !l && t && !b)
                        set = 21;
                    if (!r && !l && !t && b)
                        set = 19;
                    if (r && l && !t && !b)
                        set = 24;
                    if (!r && !l && t && b)
                        set = 20;
                    if (r && !l && t && !b && !tr)
                        set = 11;
                    if (r && !l && t && !b && tr)
                        set = 2;
                    if (!r && l && t && !b && !tl)
                        set = 12;
                    if (!r && l && t && !b && tl)
                        set = 3;
                    if (r && !l && !t && b && br)
                        set = 1;
                    if (r && !l && !t && b && !br)
                        set = 10;
                    if (!r && l && !t && b && bl)
                        set = 4;
                    if (r && !l && t && b && !tr)
                        set = 15;
                    if (r && !l && t && b && tr)
                        set = 6;
                    if (!r && l && t && b && !tl)
                        set = 17;
                    if (!r && l && t && b && tl)
                        set = 8;
                    if (r && l && !t && b && !br)
                        set = 14;
                    if (r && l && !t && b && br)
                        set = 5;
                    if (r && l && t && !b && !tr)
                        set = 16;
                    if (r && l && t && !b && tr)
                        set = 7;
                    if (!r && l && !t && b && !bl)
                        set = 13;
                    if (r && l && t && b && br && tl)
                        set = 9;
                    if (r && l && t && b && !br && !tl)
                        set = 18;

                    map[x][y] = set;
                }
                mapA.setMap(map);
            }
        }
    }
}