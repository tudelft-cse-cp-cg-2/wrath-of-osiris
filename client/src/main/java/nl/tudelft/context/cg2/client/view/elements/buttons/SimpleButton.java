package nl.tudelft.context.cg2.client.view.elements.buttons;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.Sound;

import static nl.tudelft.context.cg2.client.model.files.SoundCache.SOUNDS;

/**
 * Features a simple Javafx button with text.
 */
public class SimpleButton extends StackPane {

    private final String name;
    private Text text;
    private Sound select;

    /**
     * The simple button constructor.
     * @param name the name of the button.
     */
    public SimpleButton(String name) {
        this.name = name;
        this.draw();
        this.select = SOUNDS.get("select");
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            select.play();
        });
    }

    /**
     * Draws the button.
     */
    private void draw() {
        getStyleClass().add("simple-button");

        text = new Text(name);
        text.getStyleClass().add("text");

        this.getChildren().add(text);
    }

    /**
     * Sets the size of the button.
     * @param width  the width of the button.
     * @param height the height of the button.
     */
    public void setSize(double width, double height) {
        this.setMinSize(width, height);
        this.setMaxSize(width, height);
    }

    /**
     * gets the text of the button.
     * @return the text.
     */
    public String getText() {
        return name;
    }
}
