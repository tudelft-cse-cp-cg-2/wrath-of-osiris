package nl.tudelft.context.cg2.client.model.world.superscripts;

import javafx.scene.paint.Color;

/**
 * Features a superscript of the player name above an avatar.
 */
public class PlayerName extends Superscript {

    /**
     * The constructor.
     * @param name the name of the player.
     * @param color the color of the text.
     */
    public PlayerName(String name, Color color) {
        super(name, color.brighter(), "Comic Sans MS", null, 16D, 6D);
    }

}
