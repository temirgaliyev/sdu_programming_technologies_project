package source.Additionally;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private static Clip clip;

    /**
     * Starts Music in Background
     *
     * @throws UnsupportedAudioFileException Exception from {@link AudioSystem} Class
     * @throws IOException Exception from {@link AudioSystem} Class
     * @throws LineUnavailableException Exception from {@link AudioSystem} Class
     */
    public static void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String fileLocation = "src\\source\\res\\music_8bit.wav";
        File file = new File(fileLocation);
        clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops Music in Background
     */
    public static void stop() {
        clip.stop();
    }

}