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
import source.Additionally.CustomSettings;

import java.util.ResourceBundle;

public class Finish extends Application {

    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    /**
     * JavaFx Window opened from {@link Game}
     *
     * Show Player`s {@link Game#score} and
     * {@link Text} that returns to {@link MainMenu}
     *
     * When {@link source.Additionally.Mob} collapses with {@link source.Additionally.MyPlayer}
     * if raises {@link Game#decreaseHearts()} and shows {@link Finish}
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        Text game_over = AdditionalMethods.createText(BUNDLE.getString("game_over"), 35, blendNormal, blendNormal);
        Text u_score = AdditionalMethods.createText(BUNDLE.getString("u_score") + ":", 25, blendNormal, blendNormal);
        Text emptyText = AdditionalMethods.createText("", 40, blendNormal, blendNormal);

        Text scoreText = AdditionalMethods.createText(String.valueOf(Game.getScore()), 250, blendNormal, blendNormal);

        Text back = AdditionalMethods.createText(BUNDLE.getString("back"), 50, blendNormal, blendHover);
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> AdditionalMethods.backMain(primaryStage));

        VBox scoreVBox = new VBox(game_over, u_score, emptyText, scoreText, back);
        scoreVBox.setStyle("-fx-background-color: black");
        scoreVBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(scoreVBox));
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

}
