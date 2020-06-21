package nl.tudelft.context.cg2.client.model.files;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads and caches all sound files used in the client.
 */
public final class SoundCache {

    @SuppressFBWarnings(value = "MS_MUTABLE_COLLECTION", justification = "Map cache required here.")
    public static final Map<String, Sound> SOUNDS = new HashMap<>();

    /**
     * Loads all sound files into the sounds hashmap.
     */
    public static void loadSounds() {
        try {
            SOUNDS.put("background", Sound.loadSound(
                    SoundCache.class.getClassLoader().getResource("sounds/music.wav")));
            SOUNDS.put("win", Sound.loadSound(
                    SoundCache.class.getClassLoader().getResource("sounds/win.wav")));
            SOUNDS.put("fail", Sound.loadSound(
                    SoundCache.class.getClassLoader().getResource("sounds/fail.wav")));
            SOUNDS.put("select", Sound.loadSound(
                    SoundCache.class.getClassLoader().getResource("sounds/select.wav")));
        } catch (Exception e) {
            System.out.println("Could not load sounds!");
            e.printStackTrace();
        }
    }

}
