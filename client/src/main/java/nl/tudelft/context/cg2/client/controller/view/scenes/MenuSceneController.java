package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.MenuScene;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        scene.getJoinGameButton().setOnMouseClicked(event -> joinGameClicked());
        scene.getCreateGameButton().setOnMouseClicked(event -> createGameButtonClicked());
        scene.getQuitButton().setOnMouseClicked(event -> quitButtonClicked());
    }

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    @Override
    protected void setupKeyboardListeners() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    startGameClicked();
                    break;
                case ENTER:
                    startOpenCVClicked();
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
     * Starts the openCV instance.
     */
    private void startOpenCVClicked() {
        view.getOpenCVScene().show();
        controller.getViewController().getOpenCVSceneController().startCapture();
    }

    /**
     * Starts the game.
     */
    private void startGameClicked() {
        model.getWorld().create();
        view.getGameScene().clear();
        view.getGameScene().show();
    }

    /**
     * Callback for main Join Game button listener.
     * Retrieves available lobbies from server and loads them into the scene.
     */
    private void joinGameClicked() {
        ArrayList<Lobby> lobbies = controller.getServer().listLobbies();
        List<String> names = lobbies.stream()
                .map(l -> l.getPlayers().size() + "/5 " + l.getName()).collect(Collectors.toList());

        view.getJoinScene().setLobbyNames(names);
        view.getJoinScene().show();
    }

    /**
     * Callback for Create Game button listener.
     * Shows the create game scene.
     */
    private void createGameButtonClicked() {
        view.getCreateGameScene().show();
    }

    /**
     * Callback for Quit button listener.
     * Shuts down the javaFX application and java environment.
     */
    private void quitButtonClicked() {
        Platform.exit();
    }
}
