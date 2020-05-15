package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
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
     * @param model the model class.
     * @param view the view class.
     */
    public MenuSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        scene = view.getMenuScene();
    }

    /**
     * Sets up the mouse listeners attached to the various GUI elements.
     */
    @Override
    protected void setupMouseListeners() {
        scene.getJoinGameButton().setOnMouseClicked(event -> {
            controller.lobbyListCallback();
        });

        scene.getCreateGameButton().setOnMouseClicked(event -> {
            view.getCreateGameScene().show();
        });

        scene.getQuitButton().setOnMouseClicked(event -> {
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
                case SPACE:
                    startGame();
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Sets up the event listeners attach to various UI properties.
     */
    @Override
    protected void setupEventListeners() {

    }

    /**
     * Starts the game.
     */
    private void startGame() {
        model.getWorld().create();
        view.getGameScene().clear();
        view.getGameScene().show();
    }
}
