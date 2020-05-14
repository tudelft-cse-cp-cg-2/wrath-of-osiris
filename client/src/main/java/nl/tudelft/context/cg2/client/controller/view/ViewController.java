package nl.tudelft.context.cg2.client.controller.view;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.scenes.CreateGameSceneController;
import nl.tudelft.context.cg2.client.controller.view.scenes.GameSceneController;
import nl.tudelft.context.cg2.client.controller.view.scenes.JoinSceneController;
import nl.tudelft.context.cg2.client.controller.view.scenes.LobbySceneController;
import nl.tudelft.context.cg2.client.controller.view.scenes.MenuSceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The View Controller.
 * This class catches events from the view and acts on them.
 * View events include input events (e.g. mouse, keyboard) and change events.
 */
public class ViewController {

    private final ArrayList<SceneController> sceneControllers;
    private final MenuSceneController menuSceneController;
    private final GameSceneController gameSceneController;
    private final JoinSceneController joinSceneController;
    private final CreateGameSceneController createGameSceneController;
    private final LobbySceneController lobbySceneController;

    /**
     * The view controller constructor.
     * @param controller the controller class.
     * @param model the model class.
     * @param view the view class.
     */
    public ViewController(final Controller controller, final Model model, final View view) {
        this.menuSceneController = new MenuSceneController(controller, model, view);
        this.gameSceneController = new GameSceneController(controller, model, view);
        this.joinSceneController = new JoinSceneController(controller, model, view);
        this.createGameSceneController = new CreateGameSceneController(controller, model, view);
        this.lobbySceneController = new LobbySceneController(controller, model, view);
        this.sceneControllers = new ArrayList<>(Arrays.asList(
                menuSceneController,
                gameSceneController,
                joinSceneController,
                createGameSceneController,
                lobbySceneController
        ));

        sceneControllers.forEach(SceneController::setupListeners);
    }

    /**
     * Gets the menu scene controller.
     * @return the menu scene controller.
     */
    public GameSceneController getGameSceneController() {
        return gameSceneController;
    }

    /**
     * Gets the menu scene controller.
     * @return the menu scene controller.
     */
    public MenuSceneController getMenuSceneController() {
        return menuSceneController;
    }

    /**
     * Gets the join scene controller.
     * @return the join scene controller.
     */
    public JoinSceneController getJoinSceneController() {
        return joinSceneController;
    }

    /**
     * Gets the create game scene controller.
     * @return the create game scene controller.
     */
    public CreateGameSceneController getCreateGameSceneController() {
        return createGameSceneController;
    }

    /**
     * Gets the lobby scene controller.
     * @return the lobby scene controller.
     */
    public LobbySceneController getLobbySceneController() {
        return lobbySceneController;
    }

}
