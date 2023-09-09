import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Cat implements KeyListener {
    // move
    Timer moveTimer;
    ActionListener moveAL;
    MoveType activeMove;
    MoveType todoMove;
    boolean isStuck = true;

    // Animation
    Timer animTimer;
    ActionListener animAL;
    Image cat;
    int activeImage = 0;
    int addFactor = 1;

    public Point pixelPosition;
    public Point logicalPosition;

    private CatBoard parentBoard;
    private CatBoard2 parentBoard2;
    private CatBoard3 parentBoard3;

    public Cat(int x, int y, CatBoard catBoard) {
        activeMove = MoveType.NONE;
        todoMove = MoveType.NONE;

        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        parentBoard = catBoard;

        try {
            cat = ImageIO.read(this.getClass().getResource("/images/blackcat.png"));
        } catch (IOException e) {
            System.err.println("Cannot Read Images");
        }

        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                activeImage = activeImage + addFactor;

                if (activeImage == 4 || activeImage == 0) {
                    addFactor *= -1;
                }
            }
        };

        animTimer = new Timer(40, animAL);

        animTimer.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // update logical position
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT: // RIGHT
                                logicalPosition.x++;
                                break;
                            case LEFT: // LEFT
                                logicalPosition.x--;
                                break;
                            case UP: // UP
                                logicalPosition.y--;
                                break;
                            case DOWN: // DOWN
                                logicalPosition.y++;
                                break;
                            default:
                                break;
                        }
                        // send update message
                        parentBoard.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null));
                    }
                    isStuck = true;
                    animTimer.stop();

                    if (todoMove != MoveType.NONE && isPossibleMove1(todoMove)) {
                        activeMove = todoMove;
                        todoMove = MoveType.NONE;
                    }
                } else {
                    isStuck = false;
                    animTimer.start();
                }

                switch (activeMove) {
                    case RIGHT: // Right
                        if ((pixelPosition.x >= (parentBoard.move_x - 1) * 28) && parentBoard.isCustom) {
                            return;
                        }

                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.move_y - 1) {
                            if (parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;

                            }
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // Left
                        if ((pixelPosition.x <= 0) && parentBoard.isCustom) {
                            return;
                        }
                        if (logicalPosition.x > 0 && logicalPosition.x < parentBoard.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.move_y - 1) {
                            if (parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // Up
                        if ((pixelPosition.y <= 0) && parentBoard.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.move_y - 1) {
                            if (parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if ((pixelPosition.y >= (parentBoard.move_y - 1) * 28) && parentBoard.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.move_y - 1) {
                            if (parentBoard.map[logicalPosition.x][logicalPosition.y + 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }
                // send Message to CatBoard to check collision
                parentBoard.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));

            }
        };
        moveTimer = new Timer(7, moveAL);
        moveTimer.start();
    }

    public Cat(int x, int y, CatBoard2 catBoard2) {
        activeMove = MoveType.NONE;
        todoMove = MoveType.NONE;

        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y); //

        parentBoard2 = catBoard2;

        try {
            cat = ImageIO.read(this.getClass().getResource("/images/blackcat.png"));
        } catch (IOException e) {
            System.err.println("Cannot Read Images");
        }

        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                activeImage = activeImage + addFactor;

                if (activeImage == 4 || activeImage == 0) {
                    addFactor *= -1;
                }
            }
        };

        animTimer = new Timer(100, animAL);

        animTimer.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // update logical position
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT: // RIGHT
                                logicalPosition.x++;
                                break;
                            case LEFT: // LEFT
                                logicalPosition.x--;
                                break;
                            case UP: // UP
                                logicalPosition.y--;
                                break;
                            case DOWN: // DOWN
                                logicalPosition.y++;
                                break;
                            default:
                                break;
                        }
                        // send update message
                        parentBoard2.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null));
                    }
                    isStuck = true;
                    animTimer.stop();

                    if (todoMove != MoveType.NONE && isPossibleMove2(todoMove)) {
                        activeMove = todoMove;
                        todoMove = MoveType.NONE;
                    }
                } else {
                    isStuck = false;
                    animTimer.start();
                }

                switch (activeMove) {
                    case RIGHT: // Right
                        if ((pixelPosition.x >= (parentBoard2.move_x - 1) * 28) && parentBoard2.isCustom) {
                            return;
                        }

                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard2.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard2.move_y - 1) {
                            if (parentBoard2.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;

                            }
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // Left
                        if ((pixelPosition.x <= 0) && parentBoard2.isCustom) {
                            return;
                        }
                        if (logicalPosition.x > 0 && logicalPosition.x < parentBoard2.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard2.move_y - 1) {
                            if (parentBoard2.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // Up
                        if ((pixelPosition.y <= 0) && parentBoard2.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard2.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard2.move_y - 1) {
                            if (parentBoard2.map[logicalPosition.x][logicalPosition.y - 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if ((pixelPosition.y >= (parentBoard2.move_y - 1) * 28) && parentBoard2.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard2.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard2.move_y - 1) {
                            if (parentBoard2.map[logicalPosition.x][logicalPosition.y + 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }

                // send Message to CatBoard to check collision
                parentBoard2.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));

            }
        };
        moveTimer = new Timer(7, moveAL);
        moveTimer.start();

    }

    public Cat(int x, int y, CatBoard3 catBoard3) {
        activeMove = MoveType.NONE;
        todoMove = MoveType.NONE;

        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y); //

        parentBoard3 = catBoard3;

        try {
            cat = ImageIO.read(this.getClass().getResource("/images/blackcat.png"));
        } catch (IOException e) {
            System.err.println("Cannot Read Images");
        }

        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                activeImage = activeImage + addFactor;

                if (activeImage == 4 || activeImage == 0) {
                    addFactor *= -1;
                }
            }
        };

        animTimer = new Timer(120, animAL);

        animTimer.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // update logical position
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT: // RIGHT
                                logicalPosition.x++;
                                break;
                            case LEFT: // LEFT
                                logicalPosition.x--;
                                break;
                            case UP: // UP
                                logicalPosition.y--;
                                break;
                            case DOWN: // DOWN
                                logicalPosition.y++;
                                break;
                            default:
                                break;
                        }
                        // send update message
                        parentBoard3.dispatchEvent(new ActionEvent(this, Messenger.UPDATE, null));
                    }
                    isStuck = true;
                    animTimer.stop();

                    if (todoMove != MoveType.NONE && isPossibleMove3(todoMove)) {
                        activeMove = todoMove;
                        todoMove = MoveType.NONE;
                    }
                } else {
                    isStuck = false;
                    animTimer.start();
                }

                switch (activeMove) {
                    case RIGHT: // Right
                        if ((pixelPosition.x >= (parentBoard3.move_x - 1) * 28) && parentBoard3.isCustom) {
                            return;
                        }

                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard3.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard3.move_y - 1) {
                            if (parentBoard3.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;

                            }
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT: // Left
                        if ((pixelPosition.x <= 0) && parentBoard3.isCustom) {
                            return;
                        }
                        if (logicalPosition.x > 0 && logicalPosition.x < parentBoard3.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard3.move_y - 1) {
                            if (parentBoard3.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP: // Up
                        if ((pixelPosition.y <= 0) && parentBoard3.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard3.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard3.move_y - 1) {
                            if (parentBoard3.map[logicalPosition.x][logicalPosition.y - 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if ((pixelPosition.y >= (parentBoard3.move_y - 1) * 28) && parentBoard3.isCustom) {
                            return;
                        }
                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard3.move_x - 1
                                && logicalPosition.y >= 0 && logicalPosition.y < parentBoard3.move_y - 1) {
                            if (parentBoard3.map[logicalPosition.x][logicalPosition.y + 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y++;
                        break;
                    default:
                        break;
                }

                // send Message to CatBoard to check collision
                parentBoard3.dispatchEvent(new ActionEvent(this, Messenger.COLTEST, null));

            }
        };
        moveTimer = new Timer(5, moveAL);
        moveTimer.start();
    }

    public boolean isPossibleMove1(MoveType todoMove2) {
        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.move_x - 1 &&
                logicalPosition.y >= 0
                && logicalPosition.y < parentBoard.move_y - 1) {
            switch (todoMove2) {
                case RIGHT:
                    return !(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y + 1] > 0);
                default:
                    break;
            }
        }
        return false;
    }

    public boolean isPossibleMove2(MoveType todoMove2) {
        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard2.move_x - 1 &&
                logicalPosition.y >= 0
                && logicalPosition.y < parentBoard2.move_y - 1) {
            switch (todoMove2) {
                case RIGHT:
                    return !(parentBoard2.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard2.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard2.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard2.map[logicalPosition.x][logicalPosition.y + 1] > 0);
                default:
                    break;
            }
        }
        return false;
    }

    public boolean isPossibleMove3(MoveType todoMove2) {
        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard3.move_x - 1 &&
                logicalPosition.y >= 0
                && logicalPosition.y < parentBoard3.move_y - 1) {
            switch (todoMove2) {
                case RIGHT:
                    return !(parentBoard3.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard3.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard3.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard3.map[logicalPosition.x][logicalPosition.y + 1] > 0);
                default:
                    break;
            }
        }
        return false;
    }

    public Image getCatImage() {
        return cat;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 37: // press left arrow
                todoMove = MoveType.LEFT;
                break;
            case 38: // press up arrow
                todoMove = MoveType.UP;
                break;
            case 39: // press right arrow
                todoMove = MoveType.RIGHT;
                break;
            case 40: // press down arrow
                todoMove = MoveType.DOWN;
                break;
            case 27: // press esc that back to Exit
                keyTyped(ke);
                break;
        }
    }
}



