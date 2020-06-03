package nl.tudelft.context.cg2.client.model.world.superscripts;

import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

/**
 * The Superscript class.
 * Features a superscript displayed above a
 * world entity in the game world.
 */
public abstract class Superscript {

    private final String string;

    private final Color color;
    private final String family;
    private final FontWeight weight;

    private final double size;
    private final double spacing;

    /**
     * The superscript constructor.
     * @param string The string to display.
     * @param color the color of the font.
     * @param family the font family.
     * @param weight the font weight.
     * @param size the font size.
     * @param spacing the spacing between the superscript and the entity.
     */
    public Superscript(String string, Color color, String family,
                       FontWeight weight, double size, double spacing) {
        this.string = string;
        this.color = color;
        this.family = family;
        this.weight = weight;
        this.size = size;
        this.spacing = spacing;
    }

    /**
     * Gets the string.
     * @return the string.
     */
    public String getString() {
        return string;
    }

    /**
     * Gets the color.
     * @return the color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the font family.
     * @return the font family.
     */
    public String getFamily() {
        return family;
    }

    /**
     * Gets the string.
     * @return the string.
     */
    public FontWeight getWeight() {
        return weight;
    }

    /**
     * Gets the size.
     * @return the size.
     */
    public double getSize() {
        return size;
    }

    /**
     * Gets the spacing.
     * @return the spacing.
     */
    public double getSpacing() {
        return spacing;
    }
}
