package nl.tudelft.context.cg2.client.view.elements.buttons;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Features a simple Javafx button with text.
 */
public class SimpleButton extends StackPane {

	private final String name;
	private Text text;

	/**
	 * The sumple button constructor.
	 * @param name the name of the button.
	 */
	public SimpleButton(String name) {
		this.name = name;
		this.draw();
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
	 * @param width the width of the button.
	 * @param height the height of the button.
	 */
	public void setSize(double width, double height) {
		this.setMinSize(width, height);
		this.setMaxSize(width, height);
	}
}
