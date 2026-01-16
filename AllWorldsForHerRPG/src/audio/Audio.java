package audio;

import javax.sound.sampled.*;
import java.io.File;
import jogo.ConsoleFX;

public class Audio {

    private static boolean enabled = true;

    private Audio() {}

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void playSfx(String path) {
        if (!enabled) return;

        try {
            File audio = new File(path);
            if (!audio.exists()) return;

            AudioInputStream in = AudioSystem.getAudioInputStream(audio);
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir som: " + e.getMessage());
        }
    }

    public static void playSfxAndWait(String path, long durationMs) {
        playSfx(path);
        ConsoleFX.pause(durationMs);
    }
}