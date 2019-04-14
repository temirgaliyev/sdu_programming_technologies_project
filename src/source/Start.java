package source;

import javafx.application.Application;
import javafx.stage.Stage;
import source.Additionally.CustomSettings;
import source.windows.MainMenu;

import java.io.*;
import java.util.HashMap;

/**
 * This 'Game' is created with a lot of garbage code at the final version
 * have not any structure or something similar...
 * BUT it took a very long time to create it
 *
 * @author  Yelmurat Temirgaliyev
 * @since   BETA_1.4.3
 */

public class Start extends Application {

    public static void main(String[] args) {
        getCustomSettings();
        Application.launch(args);
    }

    /** Simple method that starts {@link source.windows.MainMenu}
     *
     * @param primaryStage The Stage of our window
     * @throws Exception Overrided #start(Stage) method must throw Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MainMenu().start(primaryStage);
    }

    /**
     * Get {@link HashMap} if exist as object
     * and set it to {@link CustomSettings}
     */
    @SuppressWarnings("unchecked")
    private static void getCustomSettings(){
        File file = new File("custom.settings");
        if (file.exists()) {
            try {
                ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                HashMap<String, Object> settingsHashMap = (HashMap<String, Object>) input.readObject();
                CustomSettings.setSettingsHashMap(settingsHashMap);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}