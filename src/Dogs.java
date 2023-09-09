import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class DogData {
    private int x;
    private int y;
    private final DogType color;

    public DogData(int x, int y, DogType color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public DogType getType() {
        return color;
    }
}

public abstract class Dogs {
    // Animation Vars
    Timer animationTime;
    ActionListener animationAL;

    // Pending Vars
    Timer pendingTimer;
    ActionListener pendingAL;

    // Move
    Timer moveTimer;
    ActionListener moveAL;
    public MoveType activeMove;
    protected boolean isStuck = true;
    boolean isPending = false;

    protected boolean isDead = false;

    public boolean isDead() {
        return isDead;
    }

    int activeImage = 0;

    public Point pixelPosition;
    public Point logicalPosition;

    Image dog;

    int dogNormal;
    int dogDead = 5;

    Finder baseReturner;

    protected CatBoard baseBoard;
    protected CatBoard2 baseBoard2;
    protected CatBoard3 baseBoard3;

    public Dogs(int x, int y, CatBoard cb, int dogDelay) {

        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        baseBoard = cb;
        activeMove = MoveType.RIGHT;

        dogNormal = dogDelay;

        loadImages();

        // animation timer
        animationAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                activeImage = (activeImage + 1) % 2;
            }
        };

        animationTime = new Timer(1, animationAL);
        animationTime.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++; // move Right that increase x a-xis
                                break;
                            case LEFT:
                                logicalPosition.x--; // move Left that decrease x a-xis
                                break;
                            case UP:
                                logicalPosition.y--; // move Up that decrease y a-xis
                                break;
                            case DOWN:
                                logicalPosition.y++; // move Down that increase y a-xis
                            default:
                                break;
                        }
                        baseBoard.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null)); // send logicalPosition
                        // update to baseBoard
                    }

                    activeMove = getMove1();
                    isStuck = true;

                } else {
                    isStuck = false;
                }

                // fix Dog movements
                switch (activeMove) {
                    // case from class move and baseboard from class catBoard
                    case RIGHT: // RIGH that move dog along X-axis(+)
                        if (pixelPosition.x >= (baseBoard.move_x - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.x + 1 < baseBoard.move_x)
                                && (baseBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0)
                                && ((baseBoard.map[logicalPosition.x + 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // LEFT that move dog along X-axis(-)
                        if (pixelPosition.x <= 0) {
                            return;
                        }
                        if ((logicalPosition.x - 1 >= 0)
                                && (baseBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0)
                                && ((baseBoard.map[logicalPosition.x - 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // UP that move dog along Y-axis(-)
                        if (pixelPosition.y <= 0) {
                            return;
                        }
                        if ((logicalPosition.y - 1 >= 0)
                                && (baseBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0)
                                && ((baseBoard.map[logicalPosition.x][logicalPosition.y - 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN: // DOWN that move dog along Y-axis(+)
                        if (pixelPosition.y >= (baseBoard.move_y - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.y + 1 < baseBoard.move_y)
                                && (baseBoard.map[logicalPosition.x][logicalPosition.y + 1] > 0)
                                && ((baseBoard.map[logicalPosition.x][logicalPosition.y + 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }
                baseBoard.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));
            }
        };

        moveTimer = new Timer(dogDelay, moveAL); // move dog
        moveTimer.start();

        // dog it's pend
        pendingAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPending = false;
                pendingTimer.stop();
            }
        };

        pendingTimer = new Timer(1, pendingAL);

        baseReturner = new Finder(cb); // send obj catBoard to constructor BFSFinder
        // start AI
        activeMove = getMove1();
    }

    public Dogs(int x, int y, CatBoard2 cb2, int dogDelay) {
        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        baseBoard2 = cb2;
        activeMove = MoveType.RIGHT;

        dogNormal = dogDelay;

        loadImages();

        // animation timer
        animationAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                activeImage = (activeImage + 1) % 2;
            }
        };

        animationTime = new Timer(1, animationAL);
        animationTime.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++; // move Right that increase x a-xis
                                break;
                            case LEFT:
                                logicalPosition.x--; // move Left that decrease x a-xis
                                break;
                            case UP:
                                logicalPosition.y--; // move Up that decrease y a-xis
                                break;
                            case DOWN:
                                logicalPosition.y++; // move Down that increase y a-xis
                            default:
                                break;
                        }
                        baseBoard2.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null)); // send logicalPosition
                        // update to baseBoard
                    }

                    activeMove = getMove2();
                    isStuck = true;

                } else {
                    isStuck = false;
                }

                // fix Dog movements
                switch (activeMove) {
                    // case from class move and baseboard from class catBoard
                    case RIGHT: // RIGH that move dog along X-axis(+)
                        if (pixelPosition.x >= (baseBoard2.move_x - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.x + 1 < baseBoard2.move_x)
                                && (baseBoard2.map[logicalPosition.x + 1][logicalPosition.y] > 0)
                                && ((baseBoard2.map[logicalPosition.x + 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // LEFT that move dog along X-axis(-)
                        if (pixelPosition.x <= 0) {
                            return;
                        }
                        if ((logicalPosition.x - 1 >= 0)
                                && (baseBoard2.map[logicalPosition.x - 1][logicalPosition.y] > 0)
                                && ((baseBoard2.map[logicalPosition.x - 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // UP that move dog along Y-axis(-)
                        if (pixelPosition.y <= 0) {
                            return;
                        }
                        if ((logicalPosition.y - 1 >= 0)
                                && (baseBoard2.map[logicalPosition.x][logicalPosition.y - 1] > 0)
                                && ((baseBoard2.map[logicalPosition.x][logicalPosition.y - 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN: // DOWN that move dog along Y-axis(+)
                        if (pixelPosition.y >= (baseBoard2.move_y - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.y + 1 < baseBoard2.move_y)
                                && (baseBoard2.map[logicalPosition.x][logicalPosition.y + 1] > 0)
                                && ((baseBoard2.map[logicalPosition.x][logicalPosition.y + 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }
                baseBoard2.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));
            }
        };

        moveTimer = new Timer(dogDelay, moveAL); // move dog
        moveTimer.start();

        pendingAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPending = false;
                pendingTimer.stop();
            }
        };

        pendingTimer = new Timer(1, pendingAL);

        baseReturner = new Finder(cb2); // send obj catBoard to constructor BFSFinder
        activeMove = getMove2();
    }

    public Dogs(int x, int y, CatBoard3 cb3, int dogDelay) {
        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        baseBoard3 = cb3;
        activeMove = MoveType.RIGHT;

        dogNormal = dogDelay;

        loadImages();

        // animation timer
        animationAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                activeImage = (activeImage + 1) % 2;
            }
        };

        animationTime = new Timer(1, animationAL);
        animationTime.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++; // move Right that increase x a-xis
                                break;
                            case LEFT:
                                logicalPosition.x--; // move Left that decrease x a-xis
                                break;
                            case UP:
                                logicalPosition.y--; // move Up that decrease y a-xis
                                break;
                            case DOWN:
                                logicalPosition.y++; // move Down that increase y a-xis
                            default:
                                break;
                        }
                        baseBoard3.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null)); // send logicalPosition
                        // update to baseBoard
                    }

                    activeMove = getMove3();
                    isStuck = true;

                } else {
                    isStuck = false;
                }

                // fix Dog movements
                switch (activeMove) {
                    // case from class move and baseboard from class catBoard
                    case RIGHT: // RIGH that move dog along X-axis(+)
                        if (pixelPosition.x >= (baseBoard3.move_x - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.x + 1 < baseBoard3.move_x)
                                && (baseBoard3.map[logicalPosition.x + 1][logicalPosition.y] > 0)
                                && ((baseBoard3.map[logicalPosition.x + 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // LEFT that move dog along X-axis(-)
                        if (pixelPosition.x <= 0) {
                            return;
                        }
                        if ((logicalPosition.x - 1 >= 0)
                                && (baseBoard3.map[logicalPosition.x - 1][logicalPosition.y] > 0)
                                && ((baseBoard3.map[logicalPosition.x - 1][logicalPosition.y] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // UP that move dog along Y-axis(-)
                        if (pixelPosition.y <= 0) {
                            return;
                        }
                        if ((logicalPosition.y - 1 >= 0)
                                && (baseBoard3.map[logicalPosition.x][logicalPosition.y - 1] > 0)
                                && ((baseBoard3.map[logicalPosition.x][logicalPosition.y - 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN: // DOWN that move dog along Y-axis(+)
                        if (pixelPosition.y >= (baseBoard3.move_y - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.y + 1 < baseBoard3.move_y)
                                && (baseBoard3.map[logicalPosition.x][logicalPosition.y + 1] > 0)
                                && ((baseBoard3.map[logicalPosition.x][logicalPosition.y + 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }
                baseBoard3.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));
            }
        };

        moveTimer = new Timer(dogDelay, moveAL); // move dog
        moveTimer.start();

        // dog it's pend
        pendingAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPending = false;
                pendingTimer.stop();
            }
        };

        pendingTimer = new Timer(1, pendingAL);

        baseReturner = new Finder(cb3); // send obj catBoard to constructor BFSFinder
        // start AI
        activeMove = getMove3();
    }

    // load Images from Resource
    public abstract void loadImages();

    // get Move Based on AI
    public abstract MoveType getMove1();

    public abstract MoveType getMove2();

    public abstract MoveType getMove3();

    public Image getDogImage() {
        return dog;
    }

    public void dead() {
        isDead = true;
        moveTimer.setDelay(dogDead);
    }
}
