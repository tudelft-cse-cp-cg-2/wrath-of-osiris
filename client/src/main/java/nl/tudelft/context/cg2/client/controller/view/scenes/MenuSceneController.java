package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.MenuScene;

/**
 * The Menu scene controller class.
 * Controls the menu scene.
 */
public class MenuSceneController extends SceneController {

    private final MenuScene scene;

    /**
     * The main scene controller.
     * Controls the input on the main scene.
     * @param controller the controller class.
     * @param view the view class.
     */
    public MenuSceneController(Controller controller, View view) {
        super(controller, view);
        scene = view.getMenuScene();
    }

    /**
     * Sets up the mouse listeners attached to the various GUI elements.
     */
    @Override
    protected void setupMouseListeners() {
        scene.getExampleButtonOne().setOnMouseClicked(event -> {
            System.out.println("CLICKED!!!");
        });

        scene.getExampleButtonTwo().setOnMouseClicked(event -> {
            System.out.println("CLICKED!!!");
        });

        scene.getExampleButtonThree().setOnMouseClicked(event -> {
            Platform.exit();
        });
    }

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    @Override
    protected void setupKeyboardListeners() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE: view.getGameScene().show(); break;
            }
        });
    }

    /**
     * Sets up the event listeners attach to various UI properties.
     */
    @Override
    protected void setupEventListeners() {

    }
}
