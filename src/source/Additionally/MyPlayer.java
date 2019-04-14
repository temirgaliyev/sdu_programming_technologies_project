package source.Additionally;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Blend;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyPlayer implements Player {
    private final int RIGHT_TO_LEFT = 1, LEFT_TO_RIGHT = 2, UP_TO_DOWN = 3, DOWN_TO_UP = 4;
    private Blend blendHover = AdditionalMethods.createBlend(null, "#ab0000", "#676767", "#ab0000");
    private int animDuration = 200; // if animation is false = 1
    private Circle ball;
    private Map map;
    private Position position;
    private boolean wall2wall;
    private boolean isMoving;
    private boolean isAlive = true;

    /**
     * Contructor of the class {@link MyPlayer}
     * Initialize local {@link #map}
     * Creates {@link Circle} MyPlayer and tune it
     *
     * @param map {@link Map}
     */
    public MyPlayer(Map map) {
        this.map = map;
        double unit = map.getUnit();
        position = new Position(map.getStartPosition().getX(), map.getStartPosition().getY());
        ball = new Circle();
        ball.setEffect(blendHover);
        ball.setCenterX(map.getStartPosition().getX() * unit + unit / 2);
        ball.setCenterY(map.getStartPosition().getY() * unit + unit / 2);
        ball.setRadius(unit / 2);
        ball.setFill(Color.RED);
        map.getChildren().add(ball);
    }

    /**
     * Overrided Method from {@link Player}
     *
     * Move to the RIGHT
     */
    @Override
    public void moveRight() {
        if (position.getX() + 1 < map.getSize()) { //If it doesn`t out of the border
            if (map.getMap()[position.getX() + 1][position.getY()] == 4) {  //If it goes to the Portal
                checkTpRight(new Position(position.getX() + 1, position.getY()));// TP to the another Portal
            } else move(new Position(1, 0)); // Just Move to The Initial Position If it`s not a Portal
        } else if (map.getMap()[0][position.getY()] == 4) {  //If it`s goes out of the border and goes to the Portal
            checkTpRight(new Position(0, position.getY()));  //TP to the another Portal
        } else if (wall2wall) teleport(new Position(-1, position.getY()), LEFT_TO_RIGHT); //If it`s goes out of the border and doesn`t go to the Portal, but wall2wall is allowed
    }

    private void checkTpRight(Position tpPos) {
        int tpToIndex = map.getPortals()[0].equals(tpPos) ? 1 : 0; // index of the Portal if the map.getPortals() array
        Position tpToPos = map.getPortals()[tpToIndex];  //get Portal on which we are Teleporting
        if (tpToPos.getX() == map.getSize() - 1) { //If Portal is on the RIGHT Edge and on the Y Position
            if (map.getMap()[0][tpToPos.getY()] != 1) // if LEFT Edge and Y position are free
                teleport(new Position(-1, tpToPos.getY()), LEFT_TO_RIGHT); // Teleport To it
        } else if (map.getMap()[tpToPos.getX() + 1][tpToPos.getY()] != 1) {  //If RIGHT of the Portal are free
            teleport(tpToPos, LEFT_TO_RIGHT); // Teleport to the coordinates
        }
    }

    /**
     * Overrided Method from {@link Player}
     *
     * Move to the LEFT
     */
    @Override
    public void moveLeft() {
        if (position.getX() > 0) {
            if (map.getMap()[position.getX() - 1][position.getY()] == 4) {
                checkTpLeft(new Position(position.getX() - 1, position.getY()));
            } else move(new Position(-1, 0));
        } else if (map.getMap()[map.getSize() - 1][position.getY()] == 4) {
            checkTpLeft(new Position(map.getSize() - 1, position.getY()));
        } else if (wall2wall) teleport(new Position(map.getSize(), position.getY()), RIGHT_TO_LEFT);
    }

    private void checkTpLeft(Position tpPos) {
        int tpToIndex = map.getPortals()[0].equals(tpPos) ? 1 : 0;
        Position tpToPos = map.getPortals()[tpToIndex];
        if (tpToPos.getX() == 0) {
            if (map.getMap()[map.getSize() - 1][tpToPos.getY()] != 1)
                teleport(new Position(map.getSize(), tpToPos.getY()), RIGHT_TO_LEFT);
        } else if (map.getMap()[tpToPos.getX() - 1][tpToPos.getY()] != 1)
            teleport(tpToPos, RIGHT_TO_LEFT);
    }

    /**
     * Overrided Method from {@link Player}
     *
     * Move to the UP
     */
    @Override
    public void moveUp() {
        if (position.getY() > 0) {
            if (map.getMap()[position.getX()][position.getY() - 1] == 4) {
                checkTpUp(new Position(position.getX(), position.getY() - 1));
            } else move(new Position(0, -1));
        } else if (map.getMap()[position.getX()][map.getSize() - 1] == 4) {
            checkTpUp(new Position(position.getX(), map.getSize() - 1));
        } else if (wall2wall) teleport(new Position(position.getX(), map.getSize()), DOWN_TO_UP);
    }

    private void checkTpUp(Position tpPos) {
        int tpToIndex = map.getPortals()[0].equals(tpPos) ? 1 : 0;
        Position tpToPos = map.getPortals()[tpToIndex];
        if (tpToPos.getY() == 0) {
            if (map.getMap()[tpToPos.getY()][map.getSize() - 1] != 1)
                teleport(new Position(tpToPos.getX(), map.getSize()), DOWN_TO_UP);
        } else if (map.getMap()[tpToPos.getX()][tpToPos.getY() - 1] != 1)
            teleport(tpToPos, DOWN_TO_UP);

    }

    /**
     * Overrided Method from {@link Player}
     *
     * Move to the DOWN
     */
    @Override
    public void moveDown() {
        if (position.getY() + 1 < map.getSize()) {
            if (map.getMap()[position.getX()][position.getY() + 1] == 4) {
                checkTpDown(new Position(position.getX(), position.getY() + 1));
            } else move(new Position(0, 1));
        } else if (map.getMap()[position.getX()][0] == 4) {
            checkTpDown(new Position(position.getX(), 0));
        } else if (wall2wall) teleport(new Position(position.getX(), -1), UP_TO_DOWN);
    }

    private void checkTpDown(Position tpPos) {
        int tpToIndex = map.getPortals()[0].equals(tpPos) ? 1 : 0;
        Position tpToPos = map.getPortals()[tpToIndex];
        if (tpToPos.getY() == map.getSize() - 1) {
            if (map.getMap()[tpToPos.getX()][0] != 1)
                teleport(new Position(tpToPos.getX(), -1), UP_TO_DOWN);
        } else if (map.getMap()[tpToPos.getX()][tpToPos.getY() + 1] != 1)
            teleport(tpToPos, UP_TO_DOWN);
    }

    /**
     * Move {@link #ball} to the new {@link Position}
     * It moves, if it`s not in Move right now
     *
     * @param to {@link Position} is the relative Position object
     */
    private void move(Position to) {
        if (map.getMap()[position.getX() + to.getX()][position.getY() + to.getY()] != 1) {
            if (!isMoving) {
                isMoving = true;
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                    ball.setCenterX(ball.getCenterX() + map.getUnit() / animDuration * to.getX());
                    ball.setCenterY(ball.getCenterY() + map.getUnit() / animDuration * to.getY());
                }));
                timeline.setCycleCount(animDuration);
                timeline.setOnFinished(event -> isMoving = false);
                timeline.play();

                position.setX(position.getX() + to.getX());
                position.setY(position.getY() + to.getY());
            }
        }
    }

    /**
     * Teleport to the new 'to' {@link Position}
     *
     * Creates new Ball and moves them separately
     * Remove newly created Ball and change coordinates of the {@link #ball}
     *
     * @param to {@link Position} teleport to it
     * @param direction direction from which {@link MyPlayer} Enters and Out Portals
     */
    private void teleport(Position to, int direction) {
        if (!isMoving) {
            final int dirY, dirX;
            switch (direction) {
                case LEFT_TO_RIGHT:
                    dirX = 1;
                    dirY = 0;
                    break;
                case RIGHT_TO_LEFT:
                    dirX = -1;
                    dirY = 0;
                    break;
                case UP_TO_DOWN:
                    dirY = 1;
                    dirX = 0;
                    break;
                case DOWN_TO_UP:
                    dirY = -1;
                    dirX = 0;
                    break;
                default:
                    dirX = 0;
                    dirY = 0;
                    break;
            }
            if (map.getMap()[to.getX() + dirX][to.getY() + dirY] != 1) {
                isMoving = true;
                Circle ball2 = new Circle();
                ball2.setCenterX(to.getX() * map.getUnit() + map.getUnit() / 2);
                ball2.setCenterY(to.getY() * map.getUnit() + map.getUnit() / 2);
                ball2.setRadius(map.getUnit() / 2);
                ball2.setFill(Color.RED);
                map.getChildren().add(ball2);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                    ball.setCenterX(ball.getCenterX() + map.getUnit() / animDuration * dirX);
                    ball.setCenterY(ball.getCenterY() + map.getUnit() / animDuration * dirY);
                    ball2.setCenterX(ball2.getCenterX() + map.getUnit() / animDuration * dirX);
                    ball2.setCenterY(ball2.getCenterY() + map.getUnit() / animDuration * dirY);
                }));

                timeline.setCycleCount(animDuration);
                timeline.setOnFinished(event -> {
                    isMoving = false;
                    ball.setCenterY(ball2.getCenterY());
                    ball.setCenterX(ball2.getCenterX());
                    map.getChildren().remove(ball2);
                });
                timeline.play();

                position.setX(to.getX() + dirX);
                position.setY(to.getY() + dirY);

            }
        }
    }


    /**
     * Replace ball to the from of the Pane
     */
    public void toFront() {
        ball.toFront();
    }

    /**
     * @param wall2wall set boolean is wall2wall allowed
     */
    public void setWall2wall(boolean wall2wall) {
        this.wall2wall = wall2wall;
    }

    /**
     * Overrided Method from {@link Player} class
     *
     * @return {@link #position} of the {@link MyPlayer}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * set isAlive to false when Player is Contact with Mob
     */
    public void setDead() {
        isAlive = false;
    }

    /**
     * @return {{@link #isAlive}}
     */
    public boolean getAlive() {
        return isAlive;
    }

    /**
     * @return {{@link #animDuration}}
     */
    public int getAnimDuration() {
        return animDuration;
    }

    /**
     * @param animDur set {@link #animDuration} to the animDur
     */
    public void setAnimDuration(int animDur) {
        this.animDuration = animDur;
    }
}
