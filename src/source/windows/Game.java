package source.windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import source.Additionally.*;
import source.Food.FoodArcade;
import source.Food.FoodPursuit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;

public class Game extends Application {
    private static int score = 0;
    private static int allScore = 0;
    private static int size;
    private static boolean portals, spawners;

    private static Text scoreText;
    private static Stage stage;

    private Map map;
    private static boolean wall2wall;
    private static int animDur,
            gm; // 0 - arcade, 1 - pursuit
    private static int GAME_ID;

    private Blend blendNormal = AdditionalMethods.createBlend("#B600FE", "#f13a00", "#feeb42", "#f13a00");
    private ResourceBundle BUNDLE = CustomSettings.getResourceBundle();

    private KeyCode RIGHT = CustomSettings.getRIGHT();
    private KeyCode LEFT = CustomSettings.getLEFT();
    private KeyCode UP = CustomSettings.getUP();
    private KeyCode DOWN = CustomSettings.getDOWN();
    private KeyCode QUIT = CustomSettings.getQUIT();

    private ArrayList<FoodArcade> foods = new ArrayList<>();

    /**
     * Constructor of the {@link Game} class.
     *
     * Initialize int GAME_ID
     *
     * Initialize from created {@link Map}:
     * int size;
     * boolean portals;
     * boolean spawners;
     *
     * @param mapFile File creates {@link Map}
     * @param gameMode int initialize static gm
     * @param animDuration int initialize static animDur
     * @param wall_wall boolean initialize static wall2wall
     * @param height double param of {@link Map}
     * @throws FileNotFoundException Exception from {@link Map}
     */
    public Game(File mapFile, int gameMode, int animDuration, boolean wall_wall, double height) throws FileNotFoundException {
        GAME_ID = new Random().nextInt();
        this.map = new Map(mapFile.getAbsolutePath(), height);
        gm = gameMode;
        animDur = animDuration;
        wall2wall = wall_wall;
        size = map.getSize();
        portals = map.isPortals();
        spawners = map.isSpawners();
    }

    /**
     * Constructor of the {@link Game} class.
     *
     * Initialize int GAME_ID
     *
     * Initialize from created {@link Map}:
     * int size;
     * boolean portals;
     * boolean spawners;
     *
     * @param mapFile File creates {@link Map}
     * @param height double param of {@link Map}
     * @throws FileNotFoundException Exception from {@link Map}
     */
    public Game(File mapFile, double height) throws FileNotFoundException {
        GAME_ID = new Random().nextInt();
        this.map = new Map(mapFile.getAbsolutePath(), height);
        gm = 0;
        animDur = 1;
        wall2wall = false;
        size = map.getSize();
        portals = map.isPortals();
        spawners = map.isSpawners();
    }

    /**
     * JavaFx Window opened from {@link Play}
     *
     * {@link #initializeAbuse(MyPlayer)}
     * Creates  player {@link MyPlayer}
     * Creates ScoreBoard
     * Set LocalGameSessionIdentifier to GLOBAL_GAME_ID
     * Detect onKeyPressed
     * Detect, if {@link MyPlayer}`s {@link Position} same with some of the {@link FoodPursuit}`s position
     * remove it and increase score
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        MyPlayer player = new MyPlayer(map);;
        initializeAbuse(player);

        Text u_score = AdditionalMethods.createText(BUNDLE.getString("u_score") + ":", 25, blendNormal, blendNormal);
        Text emptyText = AdditionalMethods.createText("", 40, blendNormal, blendNormal);
        scoreText = AdditionalMethods.createText(String.valueOf(score), 200, blendNormal, blendNormal);

        VBox scoreVBox = new VBox(u_score, emptyText, scoreText);
        scoreVBox.setAlignment(Pos.CENTER);
        scoreVBox.setStyle("-fx-background-color: black");
        scoreVBox.setPrefWidth(primaryStage.getWidth() - map.getSize() * map.getUnit());
        scoreVBox.setPrefHeight(primaryStage.getHeight());
        scoreVBox.setLayoutX(map.getSize() * map.getUnit());

        int localGameID = GAME_ID;
        Pane pane = new Pane();
        pane.getChildren().add(map);
        pane.getChildren().add(scoreVBox);
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == this.RIGHT) player.moveRight();
            else if (event.getCode() == this.LEFT) player.moveLeft();
            else if (event.getCode() == this.UP) player.moveUp();
            else if (event.getCode() == this.DOWN) player.moveDown();
            else if (event.getCode() == this.QUIT) {
                primaryStage.close();
                player.setDead();
                System.exit(0);
            }
            if (gm == 0) {
                if (player.getAlive() && localGameID == getGameId()) {
                    new Thread(() -> {
                        Iterator iterator = foods.iterator();
                        while (iterator.hasNext()) {
                            FoodArcade food = (FoodArcade) iterator.next();
                            if (player.getPosition().equals(food.getPosition())) {
                                iterator.remove();
                                try {
                                    Thread.sleep(player.getAnimDuration());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Platform.runLater(() -> map.getChildren().remove(food));
                                FoodArcade.increasePoints();
                                changeScore(FoodArcade.getPoints());
                                break;
                            }
                        }
                    }).start();
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    /**
     * Abuse method
     *
     * Set settings to player {@link MyPlayer}
     * Runs {@link Spawner} that will not work, if it`s no Spawner marks (5)
     * If GameMode is Pursuit RUN {@link FoodPursuit}class
     * If GameMode is Arcade CREATE {@link FoodArcade} Nodes in all {@link Position}
     * which {@link MyPlayer} can reach
     *
     * @param player {@link MyPlayer}
     */
    private void initializeAbuse(MyPlayer player) {
        player.setWall2wall(wall2wall);
        player.setAnimDuration(animDur);

        new Spawner(map, player);

        if (gm == 1) new FoodPursuit(map, player);
        else {
            for (int col = 0; col < map.getSize(); col++) {
                for (int row = 0; row < map.getSize(); row++) {
                    if (!(map.getMap()[row][col] == 1 || map.getMap()[row][col] == 4 || map.getMap()[row][col] == 2)) {
                        if (new Position(row, col).isConnected(map.getStartPosition(), map)) {
                            FoodArcade foodArcade = new FoodArcade(map, row, col);
                            foods.add(foodArcade);
                            map.getChildren().add(foodArcade);
                        }
                    }
                }
            }
            allScore += foods.size();
        }

        player.toFront();
    }


    /**
     * If GameMode is Arcade and all {@link FoodArcade}`s are collected,
     * then {@link AdditionalMethods#generateRandomMap(int, boolean, boolean)}
     * and start new {@link Game}
     * GLOBAL_GAME_ID will change
     *
     * If GameMode is Pursuit, {@link FoodPursuit} will ends only when {@link MyPlayer} will die
     *
     * Change maxScore on {@link CustomSettings}
     *
     * @param score int score now
     */
    public static void changeScore(int score){
        if (gm == 0){
            if (score > CustomSettings.getMaxScoreArcade()){
                CustomSettings.setMaxScoreArcade(score);
                AdditionalMethods.saveSettings();
            }
            if (score >= allScore) {
                try {
                    String path = AdditionalMethods.generateRandomMap(++size, portals, spawners);
                    Platform.runLater(() -> {
                        try {
                            new Game(new File(path), gm, animDur, wall2wall, stage.getHeight()).start(stage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e){e.printStackTrace();}
            }
        } else if (score > CustomSettings.getMaxScorePursuit()){
            CustomSettings.setMaxScorePursuit(score);
            AdditionalMethods.saveSettings();
        }

        new Thread(() -> {
            for (int i = Game.score; i < score; i++) {
                try {
                    Thread.sleep(100);
                    scoreText.setText(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Game.score = score;
        }).start();
    }

    /**
     * @return STATIC_GAME_ID
     */
    public static int getGameId() {
        return GAME_ID;
    }

    /**
     * @return STATIC_SCORE
     */
    public static int getScore() {
        return score;
    }

    /**
     * If {@link Mob} from {@link Spawner} touch {@link MyPlayer}
     * Then die
     */
    public static void decreaseHearts() {
        Platform.runLater(() -> {
            try {
                new Finish().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
