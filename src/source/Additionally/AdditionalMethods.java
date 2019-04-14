package source.Additionally;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.windows.MainMenu;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class which contains Methods that using different Classes
 */
public class AdditionalMethods {

    /**
     * @param fontSize size of the returned Font
     * @return {@link Font} returns newly created font from path with size fontSize
     */
    public static Font getFont(int fontSize) {return Font.loadFont("file:src\\source\\res\\PressStart2P.ttf", fontSize);}

    /**
     * Creates new {@link TextField} with parameters
     *
     * @param blend setEffect {@link Blend}
     * @param fontSize set fontsize for {@link Font} using {@link #getFont(int)}
     * @param maxWidth maximal width of the {@link TextField}
     * @return newly created {@link TextField}
     */
    public static TextField createTF(Blend blend, int fontSize, double maxWidth) {
        TextField textField = new TextField();
        textField.setMaxWidth(maxWidth);
        textField.setAlignment(Pos.CENTER);
        textField.setEffect(blend);
        textField.setFont(getFont(fontSize));
        textField.setStyle("-fx-background-color: #393939; -fx-text-fill: #fff0f9; -fx-background-radius: 12 12 12 12;");
        return textField;
    }

    /**
     * Creates new {@link Text} with parameters
     *
     * @param str Text of the returned {@link Text}
     * @param fontSize set fontsize for {@link Font} using {@link #getFont(int)}
     * @param blendNormal {@link Blend} effect when MouseExited
     * @param blendHover {@link Blend} effect when MouseEntered
     * @return newly created {@link Text}
     */
    public static Text createText(String str, int fontSize, Blend blendNormal, Blend blendHover) {
        Text text = new Text(str);
        text.setFill(Color.WHITE);
        text.setFont(getFont(fontSize));
        text.setEffect(blendNormal);
        text.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> text.setEffect(blendHover));
        text.addEventHandler(MouseEvent.MOUSE_EXITED,e -> text.setEffect(blendNormal));
        return text;
    }

    /**
     * creates {@link Blend} that contains imposition of many effects
     *
     * DropShadows and InnerShadows differ with different Radius and Spread
     * @param color1 {@link String} color of the DropShadow
     * @param color2 {@link String} color of second DropShadow
     * @param color3 {@link String} color of the InnerShadow
     * @param color4 {@link String} color of second DropShadow
     * @return
     */
    public static Blend createBlend(String color1, String color2, String color3, String color4) {
        //Эффекты Налаживаются Друг на Друга.

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        Blend blendLayer = new Blend();
        blendLayer.setMode(BlendMode.MULTIPLY);
        blend.setTopInput(blendLayer);

        if (color1 != null) {
            DropShadow dropShadowLayer1 = new DropShadow();
            dropShadowLayer1.setColor(Color.web(color1));
            dropShadowLayer1.setOffsetX(5);
            dropShadowLayer1.setOffsetY(5);
            dropShadowLayer1.setRadius(5);
            dropShadowLayer1.setSpread(0.2);
            blend.setBottomInput(dropShadowLayer1);
        }

        if (color2 != null) {
            DropShadow dropShadowLayer2 = new DropShadow();
            dropShadowLayer2.setColor(Color.web(color2));
            dropShadowLayer2.setRadius(20);
            dropShadowLayer2.setSpread(0.3);
            blendLayer.setBottomInput(dropShadowLayer2);
        }

        InnerShadow innerShadowLayer1 = new InnerShadow();
        innerShadowLayer1.setColor(Color.web(color3));
        innerShadowLayer1.setRadius(9);
        innerShadowLayer1.setChoke(0.8);

        InnerShadow innerShadowLayer2 = new InnerShadow();
        innerShadowLayer2.setColor(Color.web(color4));
        innerShadowLayer2.setRadius(5);
        innerShadowLayer2.setChoke(0.4);

        Blend innerShadowsBlend = new Blend();
        innerShadowsBlend.setMode(BlendMode.MULTIPLY);
        innerShadowsBlend.setBottomInput(innerShadowLayer1);
        innerShadowsBlend.setTopInput(innerShadowLayer2);
        blendLayer.setTopInput(innerShadowsBlend);

        return blend;
    }

    /**
     * Creates {@link ArrayList} with four elements that denote directions
     * @return {@link ArrayList<Position>}
     */
    public static ArrayList<Position> getDirections() {
        ArrayList<Position> directions = new ArrayList<>();
        directions.add(new Position(1, 0));
        directions.add(new Position(-1, 0));
        directions.add(new Position(0, 1));
        directions.add(new Position(0, -1));
        return directions;
    }

    /**
     * returns to {@link MainMenu}
     *
     * @param stage The Stage of our window
     */
    public static void backMain(Stage stage) {
        try {
            new MainMenu().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Randomly Generated {@link Map} with given params
     *
     * @param size int Size of the {@link Map}
     * @param portals boolean Is Portals must be on the generated {@link Map}
     * @param spawners boolean Is Spawners (Mobs) must be on the generated {@link Map}
     * @return {@link String} path of the generated {@link Map} in .txt format
     * @throws FileNotFoundException Exception from {@link PrintWriter} class
     */
    public static String generateRandomMap(int size, boolean portals, boolean spawners) throws FileNotFoundException {
        Integer[][] map = new Integer[size][size];
        int number = 0;
        String filename;
        Random random = new Random();

        while (true) {
            filename = "Map" + number + ".txt";
            File file = new File(filename);
            if (!file.exists()) break;
            number++;
        }

        //SetSpawn
        int x = (int) (Math.random() * ((size))), y = (int) (Math.random() * ((size)));
        map[x][y] = 2;

        //SetPortals
        if (portals) {
            for (int i = 0; i < 2; i++) {
                while (map[x][y] != null) {
                    x = (int) (Math.random() * size);
                    y = (int) (Math.random() * size);
                }
                map[x][y] = 4;
            }
        }

        //SetWalls
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[j][i] == null) {
                    int adding_number;
                    double r = random.nextDouble();
                    if (r < 0.33) adding_number = 1;
                    else if (spawners && r < 0.4 && i != 0 && j != 0 && i != size - 1 && j != size - 1)
                        adding_number = 5;
                    else adding_number = 0;

                    map[j][i] = adding_number;
                }
            }
        }

        //Create file and write
        File file = new File(filename);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.print(size + "\n");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                printWriter.print(map[j][i] + " ");
            }
            printWriter.print("\n");
        }
        printWriter.close();

        return file.getAbsolutePath();
    }

    /**
     * Saves {@link HashMap} from {@link CustomSettings} as object
     */
    public static void saveSettings() {
        try(
                ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("custom.settings")))
        ){
            output.writeObject(CustomSettings.getSettingsHashMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
