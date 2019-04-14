package source.Additionally;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Mob extends Circle {
    private Map map;
    private Position position;
    private int animDuration = 500;

    /**
     * Constructor of the {@link Mob}
     * initialize map and position
     * create {@link Mob}
     *
     * @param map {@link Map}
     * @param position {@link Position} of the {@link Mob}
     */
    public Mob(Map map, Position position) {
        this.map = map;
        this.position = position;

        setRadius(map.getUnit() / 2);
        setFill(Color.ORANGE);
        setCenterX(this.position.getX() * map.getUnit() + map.getUnit() / 2);
        setCenterY(this.position.getY() * map.getUnit() + map.getUnit() / 2);
    }

    /**
     * Move {@link Mob} from {@link #position} to 'to'
     *
     * @param to {@link Position}
     */
    public void move(Position to) {
        if (map.getMap()[position.getX() + to.getX()][position.getY() + to.getY()] != 1) {
            Platform.runLater(() -> {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                    setCenterX(getCenterX() + map.getUnit() / animDuration * to.getX());
                    setCenterY(getCenterY() + map.getUnit() / animDuration * to.getY());
                }));
                timeline.setCycleCount(animDuration);
                timeline.play();
            });

            position.setX(position.getX() + to.getX());
            position.setY(position.getY() + to.getY());
        }
    }

    /**
     * @return {@link Position} of the {@link Mob}
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position set {@link #position} to this param
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
