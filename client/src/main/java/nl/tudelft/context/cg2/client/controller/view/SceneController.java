package nl.tudelft.context.cg2.client.controller.view;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.view.View;

/**
 * The scene controller abstract class.
 * This is the base class of any controller class that controls a javafx scene.
 */
public abstract class SceneController {

    protected final Controller controller;
    protected final View view;

    /**
     * The SceneController constructor.
     * @param controller the controller class.
     * @param view the view class.
     */
    public SceneController(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * Sets up the listeners.
     */
    public void setupListeners() {
        this.setupMouseListeners();
        this.setupKeyboardListeners();
        this.setupEventListeners();
    }

    /**
     * Sets up the mouse listeners attached to the various GUI elements.
     */
    protected abstract void setupMouseListeners();

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    protected abstract void setupKeyboardListeners();

    /**
     * Sets up the event listeners attach to various UI properties.
     */
    protected abstract void setupEventListeners();
    

}
