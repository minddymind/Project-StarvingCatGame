
import java.awt.*;

import javax.swing.JFrame;

public class MapEditor extends JFrame { // this Class is set position of components on map
    public static MapData compileMap(String mapStr) {
        int x = mapStr.indexOf('\n');
        int y = SplitStringOfMap.SplitString(mapStr);

        MapData cusMap = new MapData(x, y);
        cusMap.setCustom(true);
        int[][] map = new int[x][y];

        // Pass map As Argument
        int i = 0, j = 0;
        char[] mapstr = mapStr.toCharArray();
        for (char ch : mapstr) {
            if (ch == '1') {
                map[i][j] = 0;
                cusMap.getDogData().add(new DogData(i, j, DogType.Brown));
            }
            if (ch == '2') {
                map[i][j] = 0;
                cusMap.getDogData().add(new DogData(i, j, DogType.Cream));
            }
            if (ch == '3') {
                map[i][j] = 0;
                cusMap.getDogData().add(new DogData(i, j, DogType.Gray));
            }
            if (ch == 'C') {
                map[i][j] = 0;
                cusMap.setCatPosition(new Point(i, j));
            }
            if (ch == 'X') {
                map[i][j] = 23;
            }
            if (ch == '_') {
                map[i][j] = 0;
                cusMap.getFishPositions().add(new Fish(i, j));
            }
            if (ch == 'F') {
                map[i][j] = 0;
                cusMap.getPowerUpPositions().add(new PowerUp(i, j));
            }
            i++;
            if (ch == '\n') {
                j++;
                i = 0;
            }
        }
        cusMap.setMap(map);
        cusMap.setCustom(true);
        return cusMap;
    }
}

class SplitStringOfMap {
    public static int SplitString(String mapStr) {
        String[] strSplit = mapStr.split("\r\n|\r|\n");
        return strSplit.length;
    }
}