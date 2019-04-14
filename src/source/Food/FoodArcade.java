package source.Food;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import source.Additionally.Map;
import source.Additionally.Position;

public class FoodArcade extends Circle {

    private static int points = 1;
    private Position position;

    /**
     * Creates {@link FoodPursuit}
     * extends from {@link Circle}
     * {@link Circle#setFill(Paint)} black
     * initialize {@link Position}
     *
     * @param map {@link Map}
     * @param row int
     * @param col int
     */
    public FoodArcade(Map map, int row, int col) {
        super((double) row * map.getUnit() + map.getUnit() / 2.0D, (double) col * map.getUnit() + map.getUnit() / 2.0D, map.getUnit() / 8.0D);
        setFill(Color.GREEN);
        this.position = new Position(row, col);
    }

    /**
     * @return {@link FoodPursuit#points}
     */
    public static int getPoints() {
        return points;
    }

    /**
     * increase {@link FoodPursuit#points} by 1
     */
    public static void increasePoints() {
        points++;
    }

    /**
     * @return {@link FoodPursuit#foodPosition}
     */
    public Position getPosition() {
        return position;
    }
}
