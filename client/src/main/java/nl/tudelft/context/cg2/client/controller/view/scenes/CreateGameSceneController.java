package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.controllers.NetworkController;
import nl.tudelft.context.cg2.client.controller.requests.CreateLobbyRequest;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
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
     * @param view the view class.
     */
    public CreateGameSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        this.scene = view.getCreateGameScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getCreateGameButton().setOnMouseClicked(event -> createGameClicked());
        scene.getLeaveButton().setOnMouseClicked(event -> leaveButtonClicked());
        scene.getPopup().setOnMouseClicked(event -> scene.closePopup());
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
                || scene.getLobbyNameField().getText().equals("")
                || scene.getPlayerNameField().getText().equals(NetworkController.EOT)
                || scene.getLobbyNameField().getText().equals(NetworkController.EOT)) {
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

        if (!req.isSuccessful()) {
            scene.showPopup("Player or lobby name already in use! Please pick a different one.");
            return;
        }

        // Set current player object.
        controller.getModel().setCurrentPlayer(PlayerFactory.createPlayer(playerName));
        controller.getViewController().getLobbySceneController()
                .scheduleLobbyUpdater(lobbyName);

        // Start game state updater thread.
        controller.setStateUpdater(new GameStateUpdater(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), controller));
        controller.getStateUpdater().start();

        // Show updated model in view.
        view.getLobbyScene().getStartButton().setVisible(true);
        view.getLobbyScene().getWaitMessage().setVisible(false);
        view.getLobbyScene().show();
    }
}
