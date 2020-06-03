package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.FetchLobbyRequest;
import nl.tudelft.context.cg2.client.controller.requests.LeaveLobbyRequest;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;

import java.util.ArrayList;
import java.util.Timer;

/**
 * The Lobby scene controller class.
 * Controls the lobby scene.
 */
public class LobbySceneController extends SceneController {

    private final LobbyScene scene;
    private Timer lobbyTimer;

    /**
     * The SceneController constructor.
     *
     * @param controller the controller class.
     * @param model the model class.
     * @param view       the view class.
     */
    public LobbySceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        this.scene = view.getLobbyScene();
    }

    @Override
    protected void setupMouseListeners() {
        scene.getStartButton().setOnMouseClicked(event -> startButtonClicked());
        scene.getLeaveButton().setOnMouseClicked(event -> leaveButtonClicked());
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }

    /**
     * Callback for the leave button listener.
     * Leaves the current lobby and forgets current player information.
     * Communicate game leaving with server.
     */
    private void leaveButtonClicked() {
        LeaveLobbyRequest req = new LeaveLobbyRequest(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut());
        stopTimer();
        req.start();
        model.setCurrentPlayer(null);
        model.setCurrentLobby(null);
        scene.setPlayerNames(new ArrayList<>());
        view.getMenuScene().show();
    }

    /**
     * Start lobby updater request.
     * @param lobbyName name of lobby to update
     */
    public void scheduleLobbyUpdater(String lobbyName) {
        this.lobbyTimer = new Timer();
        FetchLobbyRequest fetchLobbyRequest =
                new FetchLobbyRequest(controller.getNetworkController().getIn(),
                        controller.getNetworkController().getOut(), lobbyName);
        lobbyTimer.schedule(fetchLobbyRequest, 500, 500);
    }

    /**
     * Stops the lobby timer.
     */
    public void stopTimer() {
        if (lobbyTimer != null) {
            lobbyTimer.cancel();
            lobbyTimer.purge();
            lobbyTimer = null;
        }
    }

    /**
     * When the start button gets click a signal is sent to the server.
     */
    private void startButtonClicked() {
        controller.getStateUpdater().signalStart();
    }
}
