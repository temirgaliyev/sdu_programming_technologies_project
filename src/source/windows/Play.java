package source.windows;

import javafx.application.Application;
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
import source.Additionally.CustomSettings;

import java.util.ResourceBundle;

public class Play extends Application {
    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");
    private int fontSize = 35;
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    /**
     * JavaFx Window opened from {@link MainMenu}
     *
     * Shows Three {@link Text}:
     * openExistingFile: {@link OpenExistingFile}
     * generateRandomMap: {@link GenerateRandomMap}
     * backText: {@link MainMenu}
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Text openExistingFile = AdditionalMethods.createText(BUNDLE.getString("open_map"), fontSize, blendNormal, blendHover);
        openExistingFile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                new OpenExistingFile().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Text generateRandomMap = AdditionalMethods.createText(BUNDLE.getString("gen_map"), fontSize, blendNormal, blendHover);
        generateRandomMap.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                new GenerateRandomMap().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Text emptyText = AdditionalMethods.createText("", fontSize, blendNormal, blendHover);
        Text backText = AdditionalMethods.createText(BUNDLE.getString("back"), fontSize, blendNormal, blendHover);
        backText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> AdditionalMethods.backMain(primaryStage));

        VBox vBox = new VBox(openExistingFile, generateRandomMap, emptyText, backText);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vBox);
        scene.setOnKeyPressed(event -> {if (event.getCode() == KeyCode.ESCAPE) {AdditionalMethods.backMain(primaryStage);}});
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

}