package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.CreateLobbyRequest;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.PlayerFactory;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.CreateGameScene;

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
    public CreateGameSceneController(Controller controller, Model model, View view, PlayerFactory playerFactory) {
        super(controller, model, view);
        this.scene = view.getCreateGameScene();
        this.playerFactory = playerFactory;
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
        if (scene.getPlayerNameField().getText().equals("")
                || scene.getLobbyNameField().getText().equals("")) {
            return;
        }

        // Get user input from view.
        String playerName = scene.getPlayerNameField().getText();
        String lobbyName = scene.getLobbyNameField().getText();
        String password = scene.getPasswordField().getText();

        CreateLobbyRequest req = new CreateLobbyRequest(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), playerName, lobbyName, password);
        req.start();
        try {
            req.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set current player object.
        controller.getModel().setCurrentPlayer(playerFactory.createPlayer(playerName));
        controller.getViewController().getLobbySceneController()
                .scheduleLobbyUpdater(req.getResultName());

        // Start game state updater thread.
        controller.setStateUpdater(new GameStateUpdater(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), controller, playerFactory));
        controller.getStateUpdater().start();

        // Show updated model in view.
        view.getLobbyScene().getStartButton().setVisible(true);
        view.getLobbyScene().getWaitMessage().setVisible(false);
        view.getLobbyScene().show();
    }
}
