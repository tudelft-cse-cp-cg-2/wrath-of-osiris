package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.controller.controllers.NetworkController;
import nl.tudelft.context.cg2.client.controller.controllers.OpenCVController;
import nl.tudelft.context.cg2.client.controller.core.GameTimer;
import nl.tudelft.context.cg2.client.controller.requests.GameStateUpdater;
import nl.tudelft.context.cg2.client.controller.view.ViewController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
public class Controller {

    private final Model model;
    private final View view;

    private final ViewController viewController;
    private final OpenCVController openCVController;
    private final NetworkController networkController;

    private final GameTimer gameTimer;
    private GameStateUpdater gameStateUpdater;

    /**
     * Constructor for the Controller object.
     * @param model The Model object to send interaction data towards.
     * @param view The View object, containing the UI controls calling back to this controller.
     */
    public Controller(final Model model, final View view) {
        this.viewController = new ViewController(this, model, view);
        this.openCVController = new OpenCVController(this, model, view);
        this.networkController = new NetworkController();
        this.gameTimer = new GameTimer(model, view);
        this.model = model;
        this.view = view;
    }

    /**
     * Creates and starts a new game state updater.
     */
    public void initGameStateUpdater() {
        gameStateUpdater = new GameStateUpdater(networkController.getIn(),
                networkController.getOut(), this);
        gameStateUpdater.start();
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
     * Gets the networkController.
     * @return the networkController.
     */
    public NetworkController getNetworkController() {
        return networkController;
    }

    /**
     * Getter for GameStateUpdater thread.
     * @return current GameStateUdpater
     */
    public GameStateUpdater getGameStateUpdater() {
        return gameStateUpdater;
    }

    /**
     * Gets the OpenCV controller.
     * @return the controller that ontrols OpenCV logic.
     */
    public OpenCVController getOpenCVController() {
        return openCVController;
    }
}
