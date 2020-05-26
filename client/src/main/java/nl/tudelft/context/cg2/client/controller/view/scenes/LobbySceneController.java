package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.LeaveLobbyRequest;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;

import java.util.ArrayList;

/**
 * The Lobby scene controller class.
 * Controls the lobby scene.
 */
public class LobbySceneController extends SceneController {

    private final LobbyScene scene;

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
        scene.getStartButton().setOnMouseClicked(event -> System.out.println("Clicked"));
        scene.getLeaveButton().setOnMouseClicked(event -> leaveButtonClicked());
    }

    /**
     * Callback for the leave button listener.
     * Leaves the current lobby and forgets current player information.
     * Communicate game leaving with server.
     */
    private void leaveButtonClicked() {
        LeaveLobbyRequest req = new LeaveLobbyRequest(controller.getServer().getIn(),
                controller.getServer().getOut());
        controller.getEventTimer().cancel();
        controller.getEventTimer().purge();
        req.start();
        model.setCurrentPlayer(null);
        model.setCurrentLobby(null);
        scene.setPlayerNames(new ArrayList<>());
        view.getMenuScene().show();
    }

    @Override
    protected void setupKeyboardListeners() {

    }

    @Override
    protected void setupEventListeners() {

    }
}