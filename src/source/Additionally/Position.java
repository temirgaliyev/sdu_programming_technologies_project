package source.Additionally;

import java.util.ArrayList;

public class Position {

    private static ArrayList<Position> directions = AdditionalMethods.getDirections();
    private static ArrayList<Position> viewedPositions = new ArrayList<>();
    private static boolean isConnected = false;
    private int x;
    private int y;

    /**
     * Contructor of the {@link Position} class
     *
     * @param x int of {@link #x}
     * @param y int of {@link #y}
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Static Method that checks if two {@link Position} on the {@link Map}
     * @param a {@link Position}
     * @param b {@link Position}
     * @param map {@link Map}
     * @return boolean are they connected
     */
    public static boolean isConnected(Position a, Position b, Map map) {
        isConnected = false;
        viewedPositions = new ArrayList<>();
        checkConnection(a, b, map);
        return isConnected;
    }

    /**
     * Non-static Method that checks if this one is connected with b on the {@link Map}
     * @param b {@link Position}
     * @param map {@link Map}
     * @return boolean are they connected
     */
    public boolean isConnected(Position b, Map map) {
        isConnected = false;
        viewedPositions = new ArrayList<>();
        checkConnection(this, b, map);
        return isConnected;
    }

    /**
     * Private Static Recursion Function that checks if a is Connected with the b:
     * check all connections of a
     * check if b are on the connections
     * repeat recursively for all connections
     * Set {@link #isConnected} true if they are
     *
     * @param a {@link Position}
     * @param b {@link Position}
     * @param map {@link Map}
     */
    private static void checkConnection(Position a, Position b, Map map) {
        if (!isConnected) {
            if (map.getMap()[b.getX()][b.getY()] != 1 && map.getMap()[b.getX()][b.getY()] != 4) {
                for (Position d : directions) {
                    int xPos = a.getX() + d.getX(), yPos = a.getY() + d.getY();
                    if (xPos == -1) xPos = map.getSize() - 1;
                    if (xPos == map.getSize()) xPos = 0;
                    if (yPos == -1) yPos = map.getSize() - 1;
                    if (yPos == map.getSize()) yPos = 0;
                    Position dPos = new Position(xPos, yPos);
                    if (map.getMap()
                            [dPos.getX()]
                            [dPos.getY()]
                            != 1 && map.getMap()
                            [dPos.getX()]
                            [dPos.getY()]
                            != 4 && !viewedPositions.contains(dPos)) {
                        viewedPositions.add(dPos);
                        isConnected = dPos.equals(b) || isConnected;
                        checkConnection(dPos, b, map);
                    }
                }

            }
        }
    }

    /**
     * @return {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     * @param x change {@link #x}
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return {@link #y}
     */
    public int getY() {
        return y;
    }

    /**
     * @param y change {@link #y}
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Overrided Non-static Method that checks is this one have the same coordinates with the given obj
     *
     * @param obj {@link Object}
     * @return boolean are they same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            int objX = ((Position) obj).getX();
            int objY = ((Position) obj).getY();
            return this.x == objX && this.y == objY;
        }
        return false;
    }

}
