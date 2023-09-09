import java.awt.*;
import java.util.ArrayList;
public class MapData {
    private int x;
    private int y;
    private int[][] map;
    private Point catPosition;
    private Point dogBasePosition;
    private boolean isCustom;
    private final ArrayList<Fish> fishPositions;
    private final ArrayList<PowerUp> powUpPositions;
//    private final ArrayList<Move> move;
    private final ArrayList<DogData> dogData;

    public MapData() {
        fishPositions = new ArrayList<>();
        powUpPositions = new ArrayList<>();
//        move = new ArrayList<>();
        dogData = new ArrayList<>();
    }

    public MapData(int x, int y) {
        this.x = x;
        this.y = y;

        fishPositions = new ArrayList<>();
        powUpPositions = new ArrayList<>();
//        move = new ArrayList<>();
        dogData = new ArrayList<>();
    }

    public MapData(int x, int y, int[][] map, Point catPosition) {
        this.x = x;
        this.y = y;
        this.map = map;
        this.catPosition = catPosition;

        fishPositions = new ArrayList<>();
        powUpPositions = new ArrayList<>();
//        move = new ArrayList<>();
        dogData = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getCatPosition() {
        return catPosition;
    }

    public void setCatPosition(Point catPosition) {
        this.catPosition = catPosition;
    }

    public Point getDogBasePosition() {
        return dogBasePosition;
    }

    public void setDogBasePosition(Point dogBasePosition) {
        this.dogBasePosition = dogBasePosition;
    }

    public ArrayList<Fish> getFishPositions() {
        return fishPositions;
    }

    public ArrayList<PowerUp> getPowerUpPositions() {
        return powUpPositions;
    }


    public ArrayList<DogData> getDogData() {
        return dogData;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

}

