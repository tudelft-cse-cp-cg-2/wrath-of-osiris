package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.CreateGameScene;

import java.util.ArrayList;
import java.util.List;

/**
 * The Create Game scene controller class.
 * Controls the game creation scene.
 */
public class CreateGameSceneController extends SceneController {

    private final CreateGameScene scene;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param model the model class.
     * @param view       the view class.
     */
    public CreateGameSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        this.scene = view.getCreateGameScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getCreateGameButton().setOnMouseClicked(event -> createGameClicked());
        scene.getLeaveButton().setOnMouseClicked(event -> leaveButtonClicked());
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }

    /**
     * Callback for the Leave button listener.
     * Returns to the previous menu.
     */
    private void leaveButtonClicked() {
        view.getMenuScene().show();
    }

    /**
     * Callback for the Create Game button listener.
     * Creates the game with the player as host.
     */
    private void createGameClicked() {
        // Get user input from view.
        String playerName = scene.getPlayerNameField().getText();
        String lobbyName = scene.getLobbyNameField().getText();
        String password = scene.getPasswordField().getText();

        // Create model entities based on user input.
        Player player = new Player(playerName);
        List<Player> players = new ArrayList<>();
        players.add(player);

        Lobby lobby = new Lobby(lobbyName, password, players, true);

        model.setCurrentPlayer(player);
        model.setCurrentLobby(lobby);

        // Show updated model in view.
        view.getLobbyScene().setPlayerNames(lobby.getPlayerNames());
        view.getLobbyScene().getStartButton().setVisible(true);
        view.getLobbyScene().getWaitMessage().setVisible(false);
        view.getLobbyScene().show();
    }
}
