package nl.tudelft.context.cg2.client.controller.view.scenes;

import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.requests.PoseUpdater;
import nl.tudelft.context.cg2.client.controller.view.SceneController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.view.View;
import nl.tudelft.context.cg2.client.view.scenes.GameScene;

import java.util.ArrayList;
import java.util.Timer;

/**
 * The Menu scene controller class.
 * Controls the menu scene.
 */
public class GameSceneController extends SceneController {

    private final GameScene scene;
    private final World world;
    private Timer updateTimer;
    private PoseUpdater poseUpdater;

    /**
     * The main scene controller.
     * Controls the input on the main scene.
     * @param controller the controller class.
     * @param model the model class.
     * @param view the view class.
     */
    public GameSceneController(Controller controller, Model model, View view) {
        super(controller, model, view);
        scene = view.getGameScene();
        world = model.getWorld();
        model.getWorld().waveCompleted.addListener((obj, oldV, newV) -> {
            onWaveCompletion(oldV, newV);
        });
    }

    /**
     * Sets up the mouse listeners attached to the various GUI elements.
     */
    @Override
    protected void setupMouseListeners() {

    }

    /**
     * Sets up the keyboard listeners attached to the various GUI elements.
     */
    @Override
    protected void setupKeyboardListeners() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    startWorldTimer();
                    break;
                case ESCAPE:
                    leaveGame();
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
     * Starts the world timer.
     */
    private void startWorldTimer() {
        world.setInMotion(!world.isInMotion());
    }

    /**
     * Displays the menu scene in the window.
     */
    private void showMenuScene() {
        view.getMenuScene().show();
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        // Stop lobby webcam preview.
        controller.getOpenCVController().stopCapture();
        controller.getOpenCVController().startCapture();

        // Stop fetchLobby requests
        controller.getViewController().getLobbySceneController().stopTimer();

        poseUpdater = new PoseUpdater(controller.getNetworkController().getIn(),
                controller.getNetworkController().getOut(), model.getCurrentPlayer());
        updateTimer = new Timer();
        updateTimer.schedule(poseUpdater, 500, 500);

        model.getWorld().create();
        ArrayList<Player> players = new ArrayList<>(model.getCurrentLobby().getPlayers());
        model.getWorld().createPlayerAvatars(players, model.getCurrentPlayer());

        view.getGameScene().clear();
        view.getGameScene().show();
        scene.getBackgroundMusic().loop();
    }

    /**
     * When the wave is completed, this sends the pose to the server that will be compared to the
     * hole in the wall.
     * @param oldV should be false
     * @param newV should be true
     */
    private void onWaveCompletion(Boolean oldV, Boolean newV) {
        if (!oldV && newV) {
            controller.getGameStateUpdater().sendFinalPose(model.getCurrentPlayer().getPose());
        }
    }

    /**
     * Stops the game, returning the player to the lobby.
     */
    public void stopGame() {
        resetGame();
        controller.getOpenCVController().startPreview();
        view.getLobbyScene().showPopup("\nGAME OVER\n\n"
                                    + "You reached level "
                                    + world.getLevelIdx());
        view.getLobbyScene().show();
    }

    /**
     * Leaves the game and lobby, returning the player to the main menu.
     */
    private void leaveGame() {
        controller.getGameStateUpdater().signalLeave();
        resetGame();
        view.getMenuScene().showPopup("You have left a running game!");
        view.getMenuScene().show();
    }

    /**
     * Resets the game preparing it for the next run.
     */
    private void resetGame() {
        stopUpdateTimer();
        controller.getOpenCVController().stopCapture();
        world.destroy();
        scene.clear();
        scene.getBackgroundMusic().stop();
    }

    /**
     * Stops the update timer that sends
     * scheduled network updates.
     */
    public void stopUpdateTimer() {
        poseUpdater.cancel();
        poseUpdater = null;
        updateTimer.cancel();
        updateTimer.purge();
        updateTimer = null;
    }
}
