package source.Food;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import source.Additionally.Map;
import source.Additionally.MyPlayer;
import source.Additionally.Position;
import source.windows.Game;

import java.util.Random;

public class FoodPursuit {
    private final int timer = 5;
    private Map map;
    private Pane foodPane;
    private MyPlayer player;
    private Circle circle;
    private Position foodPosition;
    private Label seconds;
    private int time;
    private int points;
    private int size;
    private int[][] cells;
    private int localGameId;

    /**
     * Create {@link FoodPursuit} every {@link #time} seconds
     * while {@link MyPlayer} alive.
     *
     * @param map {@link Map}
     * @param player {@link MyPlayer}
     */
    public FoodPursuit(Map map, MyPlayer player) {
        this.map = map;
        this.foodPane = new Pane();
        this.map.getChildren().add(this.foodPane);
        this.player = player;
        this.size = this.map.getSize();
        this.cells = this.map.getMap();

        localGameId = Game.getGameId();

        Thread thread = new Thread(() -> {
            while (player.getAlive() && localGameId == Game.getGameId()) {
                this.createFood();
                Platform.runLater(() -> this.foodPane.getChildren().addAll(this.circle, this.seconds));
                for (this.time = timer * 100; this.time > 0; --this.time) {
                    Platform.runLater(() -> this.seconds.setText("" + this.time / 100));

                    if (this.player.getPosition().equals(this.foodPosition)) {
                        this.points += this.time / 100;
                        Game.changeScore(points);
                        break;
                    }

                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException ignored) {}
                }

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException ignored) {}

                Platform.runLater(() -> this.foodPane.getChildren().clear());
            }

        });
        thread.start();
    }

    /**
     * @return {@link #points}
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Create new {@link #circle} and set settings for {@link FoodPursuit}
     */
    private void createFood() {
        Random var1 = new Random();
        double var4 = this.map.getUnit();

        int var2;
        int var3;
        do {
            do {
                do {
                    var2 = var1.nextInt(this.size);
                    var3 = var1.nextInt(this.size);
                } while (!this.player.getPosition().isConnected(new Position(var2, var3), map));
            } while (this.player.getPosition().equals(new Position(var2, var3)) && player.getAlive() && localGameId == Game.getGameId());
        } while (this.cells[var2][var3] == 1 || this.cells[var2][var3] == 4 && player.getAlive() && localGameId == Game.getGameId());

        this.circle = new Circle((double) var2 * var4 + var4 / 2.0D, (double) var3 * var4 + var4 / 2.0D, var4 / 4.0D);
        this.circle.setFill(Color.GREEN);
        this.foodPosition = new Position(var2, var3);
        this.seconds = new Label("5");
        this.seconds.setTranslateX((double) var2 * var4);
        this.seconds.setTranslateY((double) var3 * var4);
        this.seconds.setTextFill(Color.web("#FFF"));
    }
}
