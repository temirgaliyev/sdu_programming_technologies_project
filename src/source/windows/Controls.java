package source.windows;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.Additionally.AdditionalMethods;
import source.Additionally.CustomSettings;

import java.util.HashMap;
import java.util.ResourceBundle;

public class Controls extends Application {
    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#B600FE", "#f13a00");
    private Blend blendHover = AdditionalMethods.createBlend("#B600FE", "#EA00F1", "#635DFE", "#B800F1");
    private Font font = AdditionalMethods.getFont(20);
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    private HashMap<String, KeyCode> controlsHashMap = new HashMap<>();

    /**
     * JavaFx Window opened from {@link Settings}
     *
     * Creates Five {@link TextField} using {@link #createTF(Blend, String)}
     * with {@link Label} created using {@link #createLabel(String)}
     * and put them to {@link HBox}, created using {@link #createHBox(Node...)}
     *
     * Creates Two {@link Text}:
     * save: Saves {@link CustomSettings} and returns to {@link Settings}
     * cancel: Returns to {@link Settings}
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeHashMap();
        TextField rightTF = createTF(blendNormal, "right");
        rightTF.setText(controlsHashMap.get("right").toString());

        TextField leftTF = createTF(blendHover, "left");
        leftTF.setText(controlsHashMap.get("left").toString());

        TextField upTF = createTF(blendNormal, "up");
        upTF.setText(controlsHashMap.get("up").toString());

        TextField downTF = createTF(blendHover, "down");
        downTF.setText(controlsHashMap.get("down").toString());

        TextField quitTF = createTF(blendNormal, "quit");
        quitTF.setText(controlsHashMap.get("quit").toString());

        Text save = AdditionalMethods.createText(BUNDLE.getString("save"), 30, blendNormal, blendHover);
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CustomSettings.setRIGHT(controlsHashMap.get("right"));
            CustomSettings.setLEFT(controlsHashMap.get("left"));
            CustomSettings.setUP(controlsHashMap.get("up"));
            CustomSettings.setDOWN(controlsHashMap.get("down"));
            CustomSettings.setQUIT(controlsHashMap.get("quit"));
            retToSettings(primaryStage);
        });

        Text cancel = AdditionalMethods.createText(BUNDLE.getString("cancel"), 30, blendNormal, blendHover);
        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {retToSettings(primaryStage);});

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                createHBox(createLabel(BUNDLE.getString("right")), rightTF),
                createHBox(createLabel(BUNDLE.getString("left")), leftTF),
                createHBox(createLabel(BUNDLE.getString("up")), upTF),
                createHBox(createLabel(BUNDLE.getString("down")), downTF),
                createHBox(createLabel(BUNDLE.getString("quit")), quitTF),
                createHBox(save, cancel)
        );

        vBox.setSpacing(50);
        vBox.setStyle("-fx-background-color: black;");
        vBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    /**
     * A simple method, that creates {@link HBox} with given {@link Node}
     * and set initial settings;
     * This method is not in the {@link AdditionalMethods} because only {@link Controls}
     * class uses it;
     *
     *
     * @param nodes Array of {@link Node}
     * @return created {@link HBox}
     */
    private HBox createHBox(Node... nodes) {
        HBox hBox = new HBox(nodes);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        return hBox;
    }

    /**
     * A simple method, that creates {@link Label} with {@link String}
     * and set initial settings;
     * This method is not in the {@link AdditionalMethods} because only {@link Controls}
     * class uses it;
     *
     *
     * @param text Text {@link String} that setted to {@link Label}
     * @return created {@link Label}
     */
    private Label createLabel(String text) {
        int width = 230;

        Label label = new Label(text);
        label.setEffect(blendNormal);
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(width);
        return label;
    }

    /**
     * A simple method, that creates {@link TextField} using {@link AdditionalMethods#createTF(Blend, int, double)}
     * The copy of the {@link AdditionalMethods#createTF(Blend, int, double)}
     * Gives setOnKeyPressed Event that change {@value}
     * of the {@link TextField} and controlsHashMap by key=name value={@value}
     *
     *
     * @param blend Effect, that setted to {@link TextField}
     * @param name Text {@link String} that setted to {@link TextField}
     * @return created {@link TextField}
     */
    private TextField createTF(Blend blend, String name) {
        TextField textField = AdditionalMethods.createTF(blend, 20, 200);
        textField.setOnKeyPressed(event -> {
            if (
                    event.getCode().isArrowKey() ||
                            event.getCode().isDigitKey() ||
                            event.getCode().isKeypadKey() ||
                            event.getCode().isLetterKey() ||
                            event.getCode() == KeyCode.SPACE
                    ) {
                if (!controlsHashMap.values().contains(event.getCode())) {
                    textField.setText(String.valueOf(event.getCode()));
                    controlsHashMap.put(name, event.getCode());
                }
            }
        });
        return textField;
    }

    /**
     * Initialize controlsHashMap:
     * key = string-name of the controls
     * value = {@link KeyCode} of the controls
     */
    private void initializeHashMap() {
        controlsHashMap.put("right", CustomSettings.getRIGHT());
        controlsHashMap.put("left", CustomSettings.getLEFT());
        controlsHashMap.put("up", CustomSettings.getUP());
        controlsHashMap.put("down", CustomSettings.getDOWN());
        controlsHashMap.put("quit", CustomSettings.getQUIT());
    }

    /**
     * Return to Settings
     *
     * @param primaryStage The Stage of our window
     */
    private void retToSettings(Stage primaryStage){
        try {
            new Settings().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}