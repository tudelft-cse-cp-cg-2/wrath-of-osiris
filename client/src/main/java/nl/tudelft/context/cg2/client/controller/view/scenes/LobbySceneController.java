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
        scene.getGuideButton().setOnMouseClicked(event -> guideButtonClicked());
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
     * Callback for the guide button listener.
     * Shows a popup containing instructions to play the game.
     */
    private void guideButtonClicked() {
        scene.showPopup(
                "Tips:"
                        + "\n* Make sure your entire posture is in the webcam preview."
                        + "\n* Press ESCAPE to leave a running game."
                        + "\n* Posture is better recognized with contrasting colors compared to "
                        + "the background color(s)."

                + "\n\nWelcome to the guide for\nWrath of Osiris!\n\n"
                + "As an archeologist you have discovered ancient treasure "
                + "and awakened the Wrath of Osiris: The pyramid is collapsing!"

                + "\n\nRun for your life and reach the highest level by taking the pose "
                + "displayed by the holes in the approaching walls, or else the pyramid "
                + "will crumble even faster! "
                + "The holes can have three positions for each arm, "
                + "and two positions for each leg. "

                + "\n\nEach level consists of several walls. "
                + "When one of your team members fails fitting through one of the holes,"
                + "the team loses one life. "
                + "How many lives your team has left is displayed in the top left "
                + "next to the heart icon. A game starts out with 10 lives."

                + "\n\nIn the lobby, a preview of your video capture is displayed, "
                + "so you can check your setup before starting. "
                + "While playing, a similar preview is also displayed in the top right"
                + "so you can verify whether you're still in the picture. "

                + "\n\nSome holes will have a number above them. "
                + "This number denotes the amount of players that successfully "
                + "have to pass through that hole to pass the wall."

                + "\n\nPress ESC to leave while playing the game, "
                + "but you will be deserting your teammates!"
                + "\n\n");
    }

    /**
     * Callback for the leave button listener.
     * Stops webcam preview capture.
     * Leaves the current lobby and forgets current player information.
     * Communicates game leaving with server.
     */
    private void leaveButtonClicked() {
        controller.getOpenCVController().stopCapture();
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
        controller.getGameStateUpdater().signalStart();
    }
}
