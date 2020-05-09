package nl.tudelft.context.cg2.client.view;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {

	private final Stage stage;
	private BaseScene shownScene;

	/**
	 * The window constructor.
	 * @param stage the window stage.
	 */
	public Window(final Stage stage) {
		this.stage = stage;
		this.shownScene = null;
		this.drawWindow();
	}

	/**
	 * Draws the window.
	 */
	private void drawWindow() {
		stage.setTitle("Hole in the wall");
		stage.setResizable(true);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setMinWidth(800D);
		stage.setMinHeight(600D);
		stage.setX(0D);
		stage.setY(0D);
	}

	/**
	 * Show a scene in the window.
	 * @param scene the scene to be displayed.
	 */
	public void showScene(BaseScene scene) {
		shownScene = scene;
		stage.setScene(scene);
	}
	
	/**
	 * Gets the window stage.
	 * @return the stage.
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Gets the base scene that is currently being displayed in the window.
	 * @return the displayed scene.
	 */
	public BaseScene getShownScene() {
		return shownScene;
	}
}
