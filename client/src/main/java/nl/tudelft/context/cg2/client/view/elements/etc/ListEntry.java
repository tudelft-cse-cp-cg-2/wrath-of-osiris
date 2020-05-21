package nl.tudelft.context.cg2.client.view.elements.etc;

import javafx.scene.control.Label;

/**
 * The list entry class.
 * Displays an entry in a listview.
 */
public class ListEntry extends Label {

    /**
     * The list entry label constructor.
     * @param name the name of the label.
     */
    public ListEntry(String name) {
        super(name);
        this.draw();
    }

    /**
     * Draws the javaFX component.
     */
    private void draw() {
        this.getStyleClass().add("name-entry");
        this.setMinWidth(150);
    }

}
