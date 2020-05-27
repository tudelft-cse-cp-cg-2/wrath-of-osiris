package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.controller.core.GameTimer;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.requests.FetchLobbyRequest;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.requests.PoseUpdater;
import nl.tudelft.context.cg2.client.controller.view.ViewController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.Server;
import nl.tudelft.context.cg2.client.view.View;

import java.util.Timer;

/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
public class Controller {

    private final ViewController viewController;
    private final GameTimer gameTimer;

    private final Model model;
    private final View view;

    private final Server server;
    private GameStateUpdater stateUpdater;

    private Timer eventTimer;

    /**
     * Constructor for the Controller object.
     * @param model The Model object to send interaction data towards.
     * @param view The View object, containing the UI controls calling back to this controller.
     */
    public Controller(final Model model, final View view) {
        this.viewController = new ViewController(this, model, view);
        this.gameTimer = new GameTimer(model, view);
        this.model = model;
        this.view = view;
        this.server = new Server();
        this.eventTimer = new Timer();
        server.connect();
    }

    /**
     * Gets the eventTimer.
     * @return the eventTimer.
     */
    public Timer getEventTimer() {
        return eventTimer;
    }

    /**
     * Gets the view.
     * @return the view.
     */
    public View getView() {
        return view;
    }

    /**
     * Gets the model.
     * @return the model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * The view controller getter.
     * @return the view controller.
     */
    public ViewController getViewController() {
        return viewController;
    }

    /**
     * The graphics timer getter.
     * @return the graphics timer.
     */
    public GameTimer getGameTimer() {
        return gameTimer;
    }

    /**
     * Gets the server.
     * @return the server.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Getter for GameStateUpdater thread.
     * @return current GameStateUdpater
     */
    public GameStateUpdater getStateUpdater() {
        return stateUpdater;
    }

    /**
     * Setter for GameStateUpdater thread.
     * @param stateUpdater new GameStateUpdater thread
     */
    public void setStateUpdater(GameStateUpdater stateUpdater) {
        this.stateUpdater = stateUpdater;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        viewController.getOpenCVSceneController().startCapture();

        // Stop fetchLobby requests
        eventTimer.cancel();

        PoseUpdater poseUpdater = new PoseUpdater(server.getIn(),
                server.getOut(), model.getCurrentPlayer());
        eventTimer = new Timer();
        eventTimer.schedule(poseUpdater, 500, 500);

        model.getWorld().create();
        view.getGameScene().clear();
        view.getGameScene().show();
    }

    /**
     * Stops the game.
     */
    public void stopGame() {
        viewController.getOpenCVSceneController().stopCapture();
        view.getLobbyScene().show();
    }

    /**
     * Updates the player's pose.
     * @param playerName player's name
     * @param pose the new pose
     */
    public void updatePose(String playerName, Pose pose) {
        try {
            int index = model.getCurrentLobby().getPlayerNames().indexOf(playerName);
            Player player = model.getCurrentLobby().getPlayers().get(index);
            player.setPose(pose);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("Player names not yet initialized");
        }
    }

    /**
     * Start lobby updater request.
     * @param index index of lobby to update
     */
    public void scheduleLobbyUpdater(int index) {
        FetchLobbyRequest fetchLobbyRequest =
                new FetchLobbyRequest(server.getIn(), server.getOut(), index);
        eventTimer.schedule(fetchLobbyRequest, 500, 500);
    }
}
