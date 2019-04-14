package source.Additionally;

import javafx.scene.effect.Blend;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map extends Pane {

    private Blend blendNormal = AdditionalMethods.createBlend("#ffff00", "#ffff00", "#ffff00", "#ffff00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#ffff00", "#635DFE", "#ffff00");
    private double unit = 0;
    private int size;
    private int[][] map;
    private Position start;
    private Position[] teleports = new Position[2];
    private boolean isPortals = false, isSpawners = false;

    /**
     * Reads File and create int[][] with the same Integers
     * Create {@link Pane} and add the following elements
     *
     * @param path {@link String} path of the {@link File}
     * @param height needed to calculate unit (unit = height / size)
     * @throws FileNotFoundException Exception of the {@link Scanner}
     */
    public Map(String path, double height) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        size = scanner.nextInt();
        unit = height / size;
        map = new int[size][size];
        start = new Position(0, 0);
        setStyle("-fx-background-color: black;");
        for (int row_y = 0; row_y < size; row_y++) {
            for (int row_x = 0; row_x < size; row_x++) {
                if (scanner.hasNextInt()) {
                    int n = scanner.nextInt();
                    if (n == 2) start = new Position(row_x, row_y);
                    else if (n == 1) {
                        Rectangle rect = new Rectangle(row_x * unit, row_y * unit, unit, unit);
                        rect.setFill(Color.YELLOW);
                        rect.setEffect(blendHover);
                        getChildren().add(rect);
                    } else if (n == 4) {
                        isPortals = true;
                        teleports[teleports[0] == null ? 0 : 1] = new Position(row_x, row_y);
                        Rectangle rect = new Rectangle(row_x * unit, row_y * unit, unit, unit);
                        rect.setFill(Color.VIOLET);
                        rect.setEffect(blendHover);
                        getChildren().add(rect);
                    } else if (n == 5) {
                        isSpawners = true;
                        Rectangle rectangle = new Rectangle(row_x * unit, row_y * unit, unit, unit);
                        rectangle.setFill(Color.BROWN);
                        getChildren().add(rectangle);
                        rectangle.toBack();
                    }
                    map[row_x][row_y] = n;
                }
            }
        }

        scanner.close();

        for (int i = 0; i < size + 1; i++) {
            Line line = new Line(0, i * unit, size * unit, i * unit);
            line.setStrokeWidth(0.1);
            line.setStroke(Paint.valueOf("#ffff00"));
            line.setEffect(blendNormal);
            getChildren().add(line);
        }

        for (int i = 0; i < size + 1; i++) {
            Line line = new Line(i * unit, 0, i * unit, size * unit);
            line.setStrokeWidth(0.1);
            line.setStroke(Paint.valueOf("#ffff00"));
            line.setEffect(blendHover);
            getChildren().add(line);
        }

    }

    /**
     * @return if there are any Portals on the {@link Map}
     */
    public boolean isPortals() {
        return isPortals;
    }

    /**
     * @return if there are any Spawners(Mobs) on the {@link Map}
     */
    public boolean isSpawners() {
        return isSpawners;
    }

    /**
     * @return {@link Position} array with two positions of two Portals
     */
    public Position[] getPortals() {
        return teleports;
    }

    /**
     * @return int {@link #unit}
     */
    public double getUnit() {
        return unit;
    }

    /**
     * @return {@link #size}
     */
    public int getSize() {
        return size;
    }

    /**
     * @return {@link #map}
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @return {@link MyPlayer}`s start {@link Position}
     */
    public Position getStartPosition() {
        return start;
    }
}
