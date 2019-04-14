package source.windows;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.Additionally.AdditionalMethods;
import source.Additionally.BackgroundMusic;
import source.Additionally.CustomSettings;

import java.util.ResourceBundle;

public class MainMenu extends Application {

    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * JavaFx Window opened from {@link source.Start}
     *
     * Just shows Main Menu with initial {@link CustomSettings}:
     * {@link Play} Class that open Play window
     * {@link Tutorial} No such class or method. Coming never. Maybe
     * {@link Settings} Class that open Settings window
     * #Exit The simplest way to out from Game // TODO: Are you sure?
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (CustomSettings.getSound())BackgroundMusic.start();

        Text maxScorePursuit = AdditionalMethods.createText(BUNDLE.getString("max_score_pursuit")+":"+String.valueOf(CustomSettings.getMaxScorePursuit()), 50, blendHover, blendHover);
        Text maxScoreArcade = AdditionalMethods.createText(BUNDLE.getString("max_score_arcade")+":"+String.valueOf(CustomSettings.getMaxScoreArcade()),50, blendHover, blendHover);

        Text play = AdditionalMethods.createText(BUNDLE.getString("play"), 50, blendNormal, blendHover);
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                new Play().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Text tutorial = AdditionalMethods.createText(BUNDLE.getString("tutorial"), 50, blendNormal, blendHover);
        Text settings = AdditionalMethods.createText(BUNDLE.getString("settings"), 50, blendNormal, blendHover);
        settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                new Settings().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Text exit = AdditionalMethods.createText(BUNDLE.getString("exit"), 50, blendNormal, blendHover);
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            primaryStage.close();
            System.exit(0);
        });

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #000");
        vbox.setSpacing(40);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(maxScorePursuit);
        vbox.getChildren().add(maxScoreArcade);
        vbox.getChildren().add(AdditionalMethods.createText("", 70, null, null));
        vbox.getChildren().add(play);
        vbox.getChildren().add(tutorial);
        vbox.getChildren().add(settings);
        vbox.getChildren().add(exit);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

}