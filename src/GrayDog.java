import javax.imageio.ImageIO;
import java.io.IOException;

public class GrayDog extends Dogs {

    public GrayDog(int x, int y, CatBoard catBoard) {
        super(x, y, catBoard, 7);
    }

    public GrayDog(int x, int y, CatBoard2 catBoard2) {
        super(x, y, catBoard2, 11);
    }

    public GrayDog(int x, int y, CatBoard3 catBoard3) {
        super(x, y, catBoard3, 12);
    }

    MoveType move = MoveType.UP;
    Finder bfs;

    @Override
    public MoveType getMove1() {
        if (!isPending) {
            if (isStuck) {
                if (move == MoveType.UP) {
                    move = MoveType.DOWN;
                } else if (move == MoveType.DOWN) {
                    move = MoveType.UP;
                }
                return move;
            }
        } else {
            return move;
        }

        if (bfs == null)
            bfs = new Finder(baseBoard);
        if (isDead) {
            return baseReturner.getMove(logicalPosition.x, logicalPosition.y, baseBoard.dogBase.x, baseBoard.dogBase.y);
        } else {
            return bfs.getMove(logicalPosition.x, logicalPosition.y, baseBoard.cat.logicalPosition.x,
                    baseBoard.cat.logicalPosition.y);
        }
    }

    @Override
    public MoveType getMove2() {
        if (!isPending) {
            if (isStuck) {
                if (move == MoveType.UP) {
                    move = MoveType.DOWN;
                } else if (move == MoveType.DOWN) {
                    move = MoveType.UP;
                }
                return move;
            }
        } else {
            return move;
        }

        if (bfs == null)
            bfs = new Finder(baseBoard2);
        if (isDead) {
            return baseReturner.getMove(logicalPosition.x, logicalPosition.y, baseBoard2.dogBase.x, baseBoard2.dogBase.y);
        } else {
            return bfs.getMove(logicalPosition.x, logicalPosition.y, baseBoard2.cat.logicalPosition.x,
                    baseBoard2.cat.logicalPosition.y);
        }
    }

    @Override
    public MoveType getMove3() {
        if (!isPending) {
            if (isStuck) {
                if (move == MoveType.UP) {
                    move = MoveType.DOWN;
                } else if (move == MoveType.DOWN) {
                    move = MoveType.UP;
                }
                return move;
            }
        } else {
            return move;
        }

        if (bfs == null)
            bfs = new Finder(baseBoard3);
        if (isDead) {
            return baseReturner.getMove(logicalPosition.x, logicalPosition.y, baseBoard3.dogBase.x, baseBoard3.dogBase.y);
        } else {
            return bfs.getMove(logicalPosition.x, logicalPosition.y, baseBoard3.cat.logicalPosition.x,
                    baseBoard3.cat.logicalPosition.y);
        }
    }

    @Override
    public void loadImages() {
        try {
            dog = ImageIO.read(this.getClass().getResource("/images/gray_dog.png"));
        } catch (IOException e) {
            System.err.println("can't read image!");
        }

    }
}

