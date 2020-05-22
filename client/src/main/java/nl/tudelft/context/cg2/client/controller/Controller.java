package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.controller.core.GameTimer;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.view.ViewController;
import nl.tudelft.context.cg2.client.model.Model;
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

    private final Timer eventTimer;

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
}
