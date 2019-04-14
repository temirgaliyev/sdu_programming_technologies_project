package source.windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.Additionally.AdditionalMethods;
import source.Additionally.BackgroundMusic;
import source.Additionally.CustomSettings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;


public class Settings extends Application {
    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");
    private int fontSize = 35;
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    /**
     * JavaFx Window opened from {@link MainMenu}
     *
     * Just shows Settings with initiated {@link CustomSettings}
     * This class could change and save {@link CustomSettings}:
     *
     * Settings:
     * Language {KZ|EN|RU}
     * Sound {On|Off}
     * Control Buttons {@link Controls}
     *
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Text langText = AdditionalMethods.createText(BUNDLE.getString("lang") + ": " + CustomSettings.getLOCALE().getLanguage().toUpperCase(), fontSize, blendNormal, blendHover);
        langText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Locale newLocale = new Locale("en", "EN");
            switch (CustomSettings.getLOCALE().getLanguage()) {
                case "kz_cyr":
                    newLocale = new Locale("ru", "RU");
                    break;
                case "ru":
                    newLocale = new Locale("en", "EN");
                    break;
                case "en":
                    newLocale = new Locale("kz_cyr", "KZ_CYR");
                    break;
            }

            CustomSettings.setLOCALE(newLocale);
            primaryStage.close();
            Platform.runLater(() -> {
                try {
                    new Settings().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        Text soundText = AdditionalMethods.createText(BUNDLE.getString("sound") + " " + (CustomSettings.getSound() ? "ON" : "OFF"), fontSize, blendNormal, blendHover);
        soundText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CustomSettings.setSound(!CustomSettings.getSound());
            soundText.setText(BUNDLE.getString("sound") + " " + (CustomSettings.getSound() ? "ON" : "OFF"));

            if (!CustomSettings.getSound()) BackgroundMusic.stop();
            else try {
                BackgroundMusic.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        });

        Text controlsText = AdditionalMethods.createText(BUNDLE.getString("controls"), fontSize, blendNormal, blendHover);
        controlsText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                new Controls().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Text emptyText = AdditionalMethods.createText("", fontSize, blendNormal, blendHover);

        Text defaultText = AdditionalMethods.createText(BUNDLE.getString("set_default_settings"), fontSize, blendNormal, blendHover);
        defaultText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CustomSettings.setLOCALE(new Locale("en", "EN"));
            CustomSettings.setRIGHT(KeyCode.RIGHT);
            CustomSettings.setLEFT(KeyCode.LEFT);
            CustomSettings.setUP(KeyCode.UP);
            CustomSettings.setDOWN(KeyCode.DOWN);
            CustomSettings.setQUIT(KeyCode.Q);
            AdditionalMethods.saveSettings();
            AdditionalMethods.backMain(primaryStage);
        });

        Text backText = AdditionalMethods.createText(BUNDLE.getString("back"), fontSize, blendNormal, blendHover);
        backText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            AdditionalMethods.saveSettings();
            AdditionalMethods.backMain(primaryStage);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(langText, soundText, controlsText, emptyText, defaultText, backText);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vBox);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) AdditionalMethods.backMain(primaryStage);
        });
        primaryStage.setScene(scene);

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

}
