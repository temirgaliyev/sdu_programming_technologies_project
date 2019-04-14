package source.Additionally;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomSettings{

    private static HashMap<String, Object> settingsHashMap = new HashMap<>();

    private static Locale LOCALE = new Locale("en", "EN");

    private static KeyCode RIGHT = KeyCode.RIGHT;
    private static KeyCode LEFT = KeyCode.LEFT;
    private static KeyCode UP = KeyCode.UP;
    private static KeyCode DOWN = KeyCode.DOWN;
    private static KeyCode QUIT = KeyCode.Q;

    private static boolean sound = true;

    private static int maxScorePursuit = 0;
    private static int maxScoreArcade = 0;

    /**
     * put all settings to  {{@link #settingsHashMap}}
     * @return {@link HashMap} {{@link #settingsHashMap}}
     */
    static HashMap<String, Object> getSettingsHashMap() {
        settingsHashMap.put("locale", LOCALE);
        settingsHashMap.put("right_key", RIGHT);
        settingsHashMap.put("left_key", LEFT);
        settingsHashMap.put("up_key", UP);
        settingsHashMap.put("down_key", DOWN);
        settingsHashMap.put("quit_key", QUIT);
        settingsHashMap.put("sound", sound);
        settingsHashMap.put("max_score_pursuit", maxScorePursuit);
        settingsHashMap.put("max_score_arcade", maxScoreArcade);
        return settingsHashMap;
    }

    /**
     * @param settingsHashMap sets to {@link #settingsHashMap} {@link HashMap}
     * get all settings from {{@link #settingsHashMap}}
     */
    public static void setSettingsHashMap(HashMap<String, Object> settingsHashMap) {
        CustomSettings.settingsHashMap = settingsHashMap;
        setLOCALE((Locale)settingsHashMap.get("locale"));
        setRIGHT((KeyCode)settingsHashMap.get("right_key"));
        setLEFT((KeyCode)settingsHashMap.get("left_key"));
        setUP((KeyCode)settingsHashMap.get("up_key"));
        setDOWN((KeyCode)settingsHashMap.get("down_key"));
        setQUIT((KeyCode)settingsHashMap.get("quit_key"));
        setSound((boolean)settingsHashMap.get("sound"));
        setMaxScorePursuit((int)settingsHashMap.get("max_score_pursuit"));
        setMaxScoreArcade((int)settingsHashMap.get("max_score_arcade"));
    }

    /**
     * @return is Background Sound On
     */
    public static boolean getSound() {
        return sound;
    }

    /**
     * @param sound Background Sound On
     */
    public static void setSound(boolean sound) {
        CustomSettings.sound = sound;
    }

    /**
     * @return {@link Locale} of the Game
     */
    public static Locale getLOCALE() {
        return LOCALE;
    }

    /**
     * @param LOCALE set {@link Locale} of the Game
     */
    public static void setLOCALE(Locale LOCALE) {
        CustomSettings.LOCALE = LOCALE;
    }

    /**
     * @return {@link KeyCode} of the RIGHT Key
     */
    public static KeyCode getRIGHT() {
        return RIGHT;
    }

    /**
     * @param RIGHT set {@link KeyCode} of the RIGHT Key
     */
    public static void setRIGHT(KeyCode RIGHT) {
        CustomSettings.RIGHT = RIGHT;
    }

    /**
     * @return {@link KeyCode} of the LEFT Key
     */
    public static KeyCode getLEFT() {
        return LEFT;
    }

    /**
     * @param LEFT set {@link KeyCode} of the LEFT Key
     */
    public static void setLEFT(KeyCode LEFT) {
        CustomSettings.LEFT = LEFT;
    }

    /**
     * @return {@link KeyCode} of the UP Key
     */
    public static KeyCode getUP() {
        return UP;
    }

    /**
     * @param UP set {@link KeyCode} of the UP Key
     */
    public static void setUP(KeyCode UP) {
        CustomSettings.UP = UP;
    }

    /**
     * @return {@link KeyCode} of the DOWN Key
     */
    public static KeyCode getDOWN() {
        return DOWN;
    }

    /**
     * @param DOWN set {@link KeyCode} of the DOWN Key
     */
    public static void setDOWN(KeyCode DOWN) {
        CustomSettings.DOWN = DOWN;
    }

    /**
     * @return {@link KeyCode} Quit
     */
    public static KeyCode getQUIT() {
        return QUIT;
    }

    /**
     * @param QUIT set {@link KeyCode} of the Quit Key
     */
    public static void setQUIT(KeyCode QUIT) {
        CustomSettings.QUIT = QUIT;
    }

    /**
     * @return {@link ResourceBundle} with {@link Locale} {@link #getLOCALE()}
     */
    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("source/res/Bundle", getLOCALE(), new UTF8Control());
    }

    /**
     * @return {@link #maxScorePursuit}
     */
    public static int getMaxScorePursuit() {
        return maxScorePursuit;
    }

    /**
     * @param maxScorePursuit set to {@link #maxScorePursuit}
     */
    public static void setMaxScorePursuit(int maxScorePursuit) {
        CustomSettings.maxScorePursuit = maxScorePursuit;
    }

    /**
     * @return {@link #maxScoreArcade}
     */
    public static int getMaxScoreArcade() {
        return maxScoreArcade;
    }

    /**
     * @param maxScoreArcade set to {@link #maxScoreArcade}
     */
    public static void setMaxScoreArcade(int maxScoreArcade) {
        CustomSettings.maxScoreArcade = maxScoreArcade;
    }
}