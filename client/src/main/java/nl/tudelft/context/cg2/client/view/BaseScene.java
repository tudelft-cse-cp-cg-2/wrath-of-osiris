package nl.tudelft.context.cg2.client.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * The base scene class.
 * Can be set in a window to show a scene.
 */
public abstract class BaseScene extends Scene {

    protected final Window window;
    protected final Pane root;

    /**
     * The base scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public BaseScene(Window window, Pane root) {
        super(root);
        this.window = window;
        this.root = root;
    }

    /**
     * Shows the scene.
     */
    public void show() {
        window.showScene(this);
        onShown();
    }

    /**
     * Draws the scene.
     */
    public abstract void draw();

    /**
     * Animates the scene.
     */
    public abstract void animate();

    /**
     * Event thrown when the scene is shown in the window.
     */
    public abstract void onShown();
}