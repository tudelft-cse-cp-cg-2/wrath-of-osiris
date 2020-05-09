package nl.tudelft.context.cg2.client.view;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nl.tudelft.context.cg2.client.view.scenes.MenuScene;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The view class.
 */
public class View {

    private final Window window;

    private final ArrayList<BaseScene> scenes;
    private final MenuScene menuScene;

    /**
     * The view constructor.
     * @param stage the javafx window being displayed to the user.
     */
    public View(final Stage stage) {
        this.window = new Window(stage);
        this.menuScene = new MenuScene(window, new StackPane());

        this.scenes = new ArrayList<>(Arrays.asList(
                menuScene
        ));

        scenes.forEach(BaseScene::draw);

        window.getStage().widthProperty().addListener((obs, oldVal, newVal) -> onResized());
        window.getStage().heightProperty().addListener((obs, oldVal, newVal) -> onResized());
    }

    /**
     * Updates everything that runs on the graphics timer.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last update.
     */
    public void update(double t, double dt) {
        if(window.getShownScene() != null) {
            window.getShownScene().animate();
        }
    }

    /**
     * Event thrown when the window is resized.
     * Can be used to update the location of various UI elements.
     */
    private void onResized() {
        if(window.getShownScene() != null) {
            window.getShownScene().onResized();
        }
    }

    /**
     * The window getter.
     * @return the window.
     */
    public Window getWindow() {
        return window;
    }

    /**
     * The menu scene getter.
     * @return the menu scene.
     */
    public MenuScene getMenuScene() {
        return menuScene;
    }
}
