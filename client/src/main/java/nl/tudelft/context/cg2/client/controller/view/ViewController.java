package nl.tudelft.context.cg2.client.controller.view;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.scenes.MenuSceneController;
import nl.tudelft.context.cg2.client.view.View;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The View Controller.
 * This class catch events from the view and acts on them.
 * View events include input events (e.g. mouse, keyboard) and change events.
 */
public class ViewController {

    private final ArrayList<SceneController> sceneControllers;
    private final MenuSceneController menuSceneController;

    /**
     * The view controller constructor.
     * @param controller the controller class.
     * @param view the view class.
     */
    public ViewController(final Controller controller, final View view) {
        this.menuSceneController = new MenuSceneController(controller, view);

        this.sceneControllers = new ArrayList<>(Arrays.asList(
                menuSceneController
        ));

        sceneControllers.forEach(SceneController::setupListeners);
    }

    /**
     * Gets the menu scene controller.
     * @return the menu scene controller.
     */
    public MenuSceneController getMenuSceneController() {
        return menuSceneController;
    }
}
