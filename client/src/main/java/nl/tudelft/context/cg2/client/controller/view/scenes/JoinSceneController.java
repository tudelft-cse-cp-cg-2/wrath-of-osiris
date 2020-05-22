package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.requests.JoinLobbyRequest;
import nl.tudelft.context.cg2.client.controller.requests.LobbyUpdater;
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
     */
    private void joinButtonClicked() {
        int index = scene.getListView().getSelectionModel().getSelectedIndex();
        String name = scene.getPlayerNameField().getText();
        JoinLobbyRequest req = new JoinLobbyRequest(controller.getServer().getIn(),
                controller.getServer().getOut(), index, name);
        req.start();
        try {
            req.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Lobby lobby = req.getResult();

        System.out.println("Selected lobby index: " + index);

        LobbyUpdater lobbyUpdater = new LobbyUpdater(controller.getServer().getIn(),
                controller.getServer().getOut(), index, view.getLobbyScene());
        controller.getEventTimer().schedule(lobbyUpdater, 2000, 2000);

        // Start game state updater thread.
        controller.setStateUpdater(new GameStateUpdater(controller.getServer().getIn(),
                controller.getServer().getOut(), index, controller));
        controller.getStateUpdater().start();

        // Retrieve and store lobby data from server.
        view.getLobbyScene().setPlayerNames(lobby.getPlayerNames());
        // todo: This is commented to test game logic without lobby creation
        //view.getLobbyScene().getStartButton().setVisible(false);
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
