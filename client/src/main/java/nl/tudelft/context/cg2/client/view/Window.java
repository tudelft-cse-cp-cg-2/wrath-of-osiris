package nl.tudelft.context.cg2.client.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The window class.
 * This class wraps the JavaFX stage which is shown as a window to the user.
 * Contains various window utility functions.
 */
public class Window {

    private final Stage stage;
    private BaseScene shownScene;

    private SimpleBooleanProperty resized;

    /**
     * The window constructor.
     *
     * @param stage the window stage.
     */
    public Window(final Stage stage) {
        this.stage = stage;
        this.shownScene = null;
        this.resized = new SimpleBooleanProperty(false);
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
     * Event thrown when the window gets resized.
     * Flashes the resized property for window observers to catch that the window was resized.
     */
    private void onResized() {
        resized.setValue(true);
        resized.setValue(false);
    }

    /**
     * Gets the window stage.
     *
     * @return the stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the base scene that is currently being displayed in the window.
     *
     * @return the displayed scene.
     */
    public BaseScene getShownScene() {
        return shownScene;
    }

    /**
     * The resized property getter.
     *
     * @return the property that flashes to true when the window is resized.
     */
    public SimpleBooleanProperty resizedProperty() {
        return resized;
    }
}
