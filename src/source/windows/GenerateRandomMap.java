package source.windows;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.Additionally.AdditionalMethods;
import source.Additionally.CustomSettings;

import java.io.File;
import java.util.ResourceBundle;

public class GenerateRandomMap extends Application {

    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");

    private boolean spawners = true, portals = true, wall2wall = true;
    private int gameMode = 0; // 0 - arcade, 1 - pursuit
    private int animDuration = 200; // 1 - none, 100 - fast, 200 - medium, 400 - slow
    private int size = 10;

    private int fontSize = 35;

    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    /**
     * JavaFx Window opened from {@link Play}
     *
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Text spawnerText = AdditionalMethods.createText(BUNDLE.getString("spawners") + " " + (spawners ? "ON" : "OFF"), fontSize, blendNormal, blendHover);
        spawnerText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            spawners = !spawners;
            spawnerText.setText(BUNDLE.getString("spawners") + " " + (spawners ? "ON" : "OFF"));
        });

        Text portalText = AdditionalMethods.createText(BUNDLE.getString("portals") + " " + (portals ? "ON" : "OFF"), fontSize, blendNormal, blendHover);
        portalText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            portals = !portals;
            portalText.setText(BUNDLE.getString("portals") + " " + (portals ? "ON" : "OFF"));
        });

        Text wall2wallText = AdditionalMethods.createText(BUNDLE.getString("wall2wall") + " " + (wall2wall ? "ON" : "OFF"), fontSize, blendNormal, blendHover);
        wall2wallText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            wall2wall = !wall2wall;
            wall2wallText.setText(BUNDLE.getString("wall2wall") + " " + (wall2wall ? "ON" : "OFF"));
        });

        Text gameModeText = AdditionalMethods.createText(BUNDLE.getString("gm") + " " + (gameMode == 0 ? BUNDLE.getString("arcade") : BUNDLE.getString("pursuit")), fontSize, blendNormal, blendHover);
        gameModeText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameMode = gameMode == 0 ? 1 : 0;
            gameModeText.setText(BUNDLE.getString("gm") + " " + (gameMode == 0 ? BUNDLE.getString("arcade") : BUNDLE.getString("pursuit")));
        });


        Text animText = AdditionalMethods.createText(BUNDLE.getString("anim_duration") + " " + BUNDLE.getString("medium_"), fontSize, blendNormal, blendHover);
        animText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            animDuration += animDuration;
            if (animDuration == 2) animDuration = 100;
            if (animDuration > 400) animDuration = 1;
            String animStr = "";
            switch (animDuration) {
                case 1:
                    animStr = BUNDLE.getString("none_");
                    break;
                case 100:
                    animStr = BUNDLE.getString("fast_");
                    break;
                case 200:
                    animStr = BUNDLE.getString("medium_");
                    break;
                case 400:
                    animStr = BUNDLE.getString("slow_");
                    break;
            }
            animText.setText(BUNDLE.getString("anim_duration") + " " + animStr);
        });

        TextField sizeTF1 = AdditionalMethods.createTF(blendNormal, 20, 85);
        sizeTF1.setText(String.valueOf(size));
        TextField sizeTF2 = AdditionalMethods.createTF(blendNormal, 20, 85);
        sizeTF2.setText(String.valueOf(size));
        ChangeListener<String> sizeChangeListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sizeTF1.setText(newValue.replaceAll("[^\\d]", ""));
                sizeTF2.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (!newValue.isEmpty()) {
                if (Integer.parseInt(newValue) <= 100) {
                    sizeTF1.setText(newValue);
                    sizeTF2.setText(newValue);
                    size = Integer.parseInt(newValue);
                } else {
                    size = 100;
                    sizeTF1.setText("100");
                    sizeTF2.setText("100");
                }
            } else {
                size = 10;
                sizeTF1.setText("");
                sizeTF2.setText("");
            }
        };

        Text mapSize = AdditionalMethods.createText(BUNDLE.getString("map_size"), fontSize, blendNormal, blendNormal);

        sizeTF1.textProperty().addListener(sizeChangeListener);
        sizeTF2.textProperty().addListener(sizeChangeListener);
        HBox sizeHBox = new HBox(mapSize, sizeTF1, AdditionalMethods.createText("X", fontSize, blendNormal, blendNormal), sizeTF2);
        sizeHBox.setAlignment(Pos.CENTER);
        sizeHBox.setSpacing(20);

        Text emptyText = AdditionalMethods.createText("", fontSize, blendNormal, blendHover);

        Text generateText = AdditionalMethods.createText(BUNDLE.getString("generate"), fontSize, blendNormal, blendHover);
        generateText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String filename;
            try {
                filename = AdditionalMethods.generateRandomMap(size, portals, spawners);
                new Game(new File(filename), gameMode, animDuration, wall2wall, primaryStage.getHeight()).start(primaryStage);
            } catch (Exception e) {e.printStackTrace();}
        });

        Text backText = AdditionalMethods.createText(BUNDLE.getString("back"), fontSize, blendNormal, blendHover);
        backText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> AdditionalMethods.backMain(primaryStage));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(spawnerText, portalText, wall2wallText, gameModeText, animText, sizeHBox, emptyText, generateText, backText);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);
        vBox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vBox);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) AdditionalMethods.backMain(primaryStage);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello!");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }



}
