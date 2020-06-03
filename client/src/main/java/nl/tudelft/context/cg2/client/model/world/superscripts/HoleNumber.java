package nl.tudelft.context.cg2.client.model.world.superscripts;

import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

/**
 * Features a superscript of a number above a hole in the wall.
 */
public class HoleNumber extends Superscript {

    /**
     * The number above the hole.
     * @param value the value of the number.
     */
    public HoleNumber(int value) {
        super(Integer.toString(value), Color.BLACK, "Comic Sans MS",
                FontWeight.BOLD, 100D, 150D);
    }

}
