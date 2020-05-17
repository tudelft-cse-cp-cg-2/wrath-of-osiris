package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
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
     * It is assumed that list 'lobbies' corresponds to JoinScene's 'lobbyEntries'.
     */
    private void joinButtonClicked() {
        int index = scene.getListView().getSelectionModel().getSelectedIndex();
        String name = scene.getPlayerNameField().getText();
        Lobby lobby = controller.getServer().joinLobby(index, name);

        System.out.println("Selected lobby index: " + index);

        // Retrieve and store lobby data from server.
        view.getLobbyScene().setPlayerNames(lobby.getPlayerNames());
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
