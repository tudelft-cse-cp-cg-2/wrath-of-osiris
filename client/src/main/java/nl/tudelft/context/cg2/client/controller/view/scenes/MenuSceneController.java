package nl.tudelft.context.cg2.client.controller.view.scenes;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.ListLobbiesRequest;
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
        scene.getPopup().setOnMouseClicked(event -> scene.closePopup());
    }

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    @Override
    protected void setupKeyboardListeners() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    startOpenCVClicked();
                    break;
                case ESCAPE:
                    scene.closePopup();
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
        controller.getOpenCVController().startCapture();
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
        if (controller.getNetworkController().connect()) {
            ListLobbiesRequest req =
                    new ListLobbiesRequest(controller.getNetworkController().getIn(),
                    controller.getNetworkController().getOut());
            req.start();
            try {
                req.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Lobby> lobbies = req.getResult();
            List<String> names = lobbies.stream()
                    .map(l -> l.getPlayers().size() + "/5 " +
                            l.getName()).collect(Collectors.toList());

            view.getJoinScene().setLobbyNames(names);
            view.getJoinScene().show();
        } else {
            scene.showPopup("There was an error connecting to the server!");
        }
    }

    /**
     * Callback for Create Game button listener.
     * Shows the create game scene.
     */
    private void createGameButtonClicked() {
        if (controller.getNetworkController().connect()) {
            view.getCreateGameScene().show();
        } else {
            scene.showPopup("There was an error connecting to the server!");
        }
    }

    /**
     * Callback for Quit button listener.
     * Shuts down the javaFX application and java environment.
     */
    private void quitButtonClicked() {
        Platform.exit();
    }
}
