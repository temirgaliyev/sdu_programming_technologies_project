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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import source.Additionally.AdditionalMethods;
import source.Additionally.CustomSettings;

import java.io.File;
import java.util.ResourceBundle;

public class OpenExistingFile extends Application {

    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");

    private boolean wall2wall = true;
    private int gameMode = 0; // 0 - arcade, 1 - pursuit
    private int animDuration = 200; // 1 - none, 100 - fast, 200 - medium, 400 - slow

    private int fontSize = 35;
    private File file;
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * JavaFx Window opened from {@link Play}
     *
     * Shows Six {@link Text}:
     * openText: open {@link FileChooser} to get MapFile
     * wall2wallText: move from end of the one wall to start of the opposiot
     * gameModeText: set mode of the game {Arcade|Pursuit}
     * animText: Duration of Moving Animation {None|Fast|Medium|Slow}
     * playText: {@link Game}
     * backText: {@link MainMenu}
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        Text openText = AdditionalMethods.createText(BUNDLE.getString("choose_map"), fontSize, blendHover, blendHover);
        openText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(BUNDLE.getString("open_map"));
            File choosedFile = fileChooser.showOpenDialog(primaryStage);
            file = choosedFile == null ? file : choosedFile;
            openText.setText(BUNDLE.getString("file") + ": " + file.getAbsolutePath());
        });
        vBox.getChildren().add(openText);

        createPanesAbuse(vBox);

        Text emptyText = AdditionalMethods.createText("", fontSize, blendNormal, blendHover);

        Text playText = AdditionalMethods.createText(BUNDLE.getString("play"), fontSize, blendNormal, blendHover);
        playText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if (file != null)new Game(file, gameMode, animDuration, wall2wall, primaryStage.getHeight()).start(primaryStage);
                else openText.setEffect(blendHover);
            }catch (Exception e) {e.printStackTrace();}
        });

        Text backText = AdditionalMethods.createText(BUNDLE.getString("back"), fontSize, blendNormal, blendHover);
        backText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> AdditionalMethods.backMain(primaryStage));

        vBox.getChildren().addAll(emptyText, playText, backText);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);
        vBox.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(vBox);
        scene.setOnKeyPressed(event -> {if (event.getCode() == KeyCode.ESCAPE) AdditionalMethods.backMain(primaryStage);});
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello!");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void createPanesAbuse(VBox vBox) {
        Text wall2wallText = AdditionalMethods.createText(BUNDLE.getString("wall2wall") + " " + (wall2wall ? "ON" : "OFF"), fontSize, blendNormal, blendHover);
        wall2wallText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            wall2wall = !wall2wall;
            wall2wallText.setText(BUNDLE.getString("wall2wall") + " " + (wall2wall ? "ON" : "OFF"));
        });

        Text gameModeText = AdditionalMethods.createText(BUNDLE.getString("gm") + " " + (gameMode == 0 ? "ARCADE" : "PURSUIT"), fontSize, blendNormal, blendHover);
        gameModeText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameMode = gameMode == 0 ? 1 : 0;
            gameModeText.setText(BUNDLE.getString("gm") + " " + (gameMode == 0 ? "ARCADE" : "PURSUIT"));
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

        vBox.getChildren().addAll(wall2wallText, gameModeText, animText);

    }

}
