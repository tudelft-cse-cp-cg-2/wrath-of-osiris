package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.requests.JoinLobbyRequest;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.JoinScene;

/**
 * The Lobby scene controller class.
 * Controls the lobby scene.
 */
public class JoinSceneController extends SceneController {

    private final JoinScene scene;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param model the model class.
     * @param view       the view class.
     */
    public JoinSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        this.scene = view.getJoinScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getJoinButton().setOnMouseClicked(event -> joinButtonClicked());
        scene.getBackButton().setOnMouseClicked(event -> backButtonClicked());
    }

    /**
     * Callback for the back button listener.
     * Shows the previous scene.
     */
    private void backButtonClicked() {
        view.getMenuScene().show();
    }

    /**
     * Callback for the joinScene button listener.
     * Joins the game with the player as guest.
     */
    private void joinButtonClicked() {
        int index = scene.getListView().getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        String lobbyName = model.getAvailableLobbies().get(index).getName();
        String playerName = scene.getPlayerNameField().getText();

        // Request server to join lobby.
        JoinLobbyRequest req = new JoinLobbyRequest(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), lobbyName, playerName);
        req.start();
        try {
            req.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set current player object.
        controller.getModel().setCurrentPlayer(new Player(playerName));
        controller.getViewController().getLobbySceneController().scheduleLobbyUpdater(lobbyName);

        // Start game state updater thread.
        controller.setStateUpdater(new GameStateUpdater(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), controller));
        controller.getStateUpdater().start();

        // Switch to lobby scene.
        view.getLobbyScene().getStartButton().setVisible(false);
        view.getLobbyScene().getWaitMessage().setVisible(true);
        view.getLobbyScene().show();
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }

}
