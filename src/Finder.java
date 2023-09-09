/* this Class find position that near cat for chaser dogs */

import java.awt.*;
public class Finder{ // Board find score
        int[][] map;
        int moveX, moveY, i, j;

        public Finder(CatBoard cb) {
            moveX = cb.move_x;
            moveY = cb.move_y;

            // initate find cat on map
            map = new int[cb.move_x][cb.move_y];
            for (i = 0; i < moveY; i++) {
                for (j = 0; j < moveX; j++) {
                    if (cb.map[j][i] > 0 && cb.map[j][i] < 26)
                        map[j][i] = 1;
                    else
                        map[j][i] = 0;
                }
            }
        }

        public Finder(CatBoard2 cb2) {
            moveX = cb2.move_x;
            moveY = cb2.move_y;

            // initate find cat on map
            map = new int[cb2.move_x][cb2.move_y];
            for (i = 0; i < moveY; i++) {
                for (j = 0; j < moveX; j++) {
                    if (cb2.map[j][i] > 0 && cb2.map[j][i] < 26)
                        map[j][i] = 1;
                    else
                        map[j][i] = 0;
                }
            }
        }

        public Finder(CatBoard3 cb3) {
            moveX = cb3.move_x;
            moveY = cb3.move_y;

            // initate find cat on map
            map = new int[cb3.move_x][cb3.move_y];
            for (i = 0; i < moveY; i++) {
                for (j = 0; j < moveX; j++) {
                    if (cb3.map[j][i] > 0 && cb3.map[j][i] < 26)
                        map[j][i] = 1;
                    else
                        map[j][i] = 0;
                }
            }
        }

private static class MazeCell {
    int x, y;

    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

    private boolean isUsable(int i, int j, boolean[][] mark) {
        return (i >= 0 && i < moveX && j >= 0 && j < moveY && map[i][j] == 0 && !mark[i][j]);
    }

    public MoveType getMove(int x, int y, int topX, int topY) {
        int k, i, j; // i like X-Axis and j like Y-Axis
        // already reached
        if (x == topX && y == topY)
            return MoveType.NONE;

        MazeCell[][] mazeTable = new MazeCell[moveX][moveY];
        Point[][] baseTable = new Point[moveX][moveY];
        boolean[][] mark = new boolean[moveX][moveY];

        for (i = 0; i < moveX; i++) {
            for (j = 0; j < moveY; j++) {
                mark[i][j] = false;
            }
        }

        MazeCell[] mCells = new MazeCell[2000];
        int size = 1;

        MazeCell start = new MazeCell(x, y);
        mazeTable[x][y] = start;
        mCells[0] = start; // Q
        mark[x][y] = true;

        for (k = 0; k < size; k++) {
            i = mCells[k].x;
            j = mCells[k].y;

            // RIGHT
            if (isUsable(i + 1, j, mark)) {
                MazeCell m = new MazeCell(i + 1, j);
                mazeTable[i + 1][j] = m;
                mCells[size] = m;
                size++;
                mark[i + 1][j] = true;
                baseTable[i + 1][j] = new Point(i, j);
            }

            // LEFT
            if (isUsable(i - 1, j, mark)) {
                MazeCell m = new MazeCell(i - 1, j);
                mazeTable[i - 1][j] = m;
                mCells[size] = m;
                size++;
                mark[i - 1][j] = true;
                baseTable[i - 1][j] = new Point(i, j);
            }

            // UP
            if (isUsable(i, j - 1, mark)) {
                MazeCell m = new MazeCell(i, j - 1);
                mazeTable[i][j - 1] = m;
                mCells[size] = m;
                size++;
                mark[i][j - 1] = true;
                baseTable[i][j - 1] = new Point(i, j);
            }

            // DOWN
            if (isUsable(i, j + 1, mark)) {
                MazeCell m = new MazeCell(i, j + 1);
                mazeTable[i][j + 1] = m;
                mCells[size] = m;
                size++;
                mark[i][j + 1] = true;
                baseTable[i][j + 1] = new Point(i, j);
            }
        }

        int tableTopX = topX;
        int tableTopY = topY;
        MazeCell t = mazeTable[tableTopX][tableTopY];
        MazeCell tleft = null;
        while (t != start) {
            Point tablePoint = baseTable[tableTopX][tableTopY];
            tableTopX = tablePoint.x;
            tableTopY = tablePoint.y;
            tleft = t;
            t = mazeTable[tableTopX][tableTopY];
        }

        if (tleft != null && x == tleft.x - 1 && y == tleft.y)
            return MoveType.RIGHT;
        if (x == tleft.x + 1 && y == tleft.y)
            return MoveType.LEFT;
        if (x == tleft.x && y == tleft.y - 1)
            return MoveType.DOWN;
        if (x == tleft.x && y == tleft.y + 1) {
            return MoveType.UP;
        }
        return MoveType.NONE;
    }
}
