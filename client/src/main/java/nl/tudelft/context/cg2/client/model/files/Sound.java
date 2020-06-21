package nl.tudelft.context.cg2.client.model.files;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

/**
 * Serves as a wrapper class for the Clip class.
 */
public class Sound {

    private Clip clip;
    private float volume;

    /**
     * Make sure sound objects can only be created by the loadSound method.
     * @param clip The audio clip.
     */
    Sound(Clip clip) {
        this.clip = clip;

        volume = 1f;
    }

    /**
     * Plays the sound from the start.
     */
    public void play() {
        stop();

        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Starts looping the sound from the current frame position.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stop playing the sound.
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Gets the volume of the sound.
     * @return The volume between 0.0f and 1.0f.
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Set the volume of the sound.
     * @param volume The volume between 0.0f and 1.0f.
     */
    public void setVolume(float volume) {
        this.volume = volume;

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = range * volume + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    /**
     * Loads a sound.
     * @param path The path to the sound.
     * @return The sound.
     * @throws Exception When the sound could not be loaded.
     */
    public static Sound loadSound(URL path) throws Exception {
        Clip clip = AudioSystem.getClip();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path);
        clip.open(audioInputStream);

        return new Sound(clip);
    }
}

