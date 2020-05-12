package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.controller.core.GraphicsTimer;
import nl.tudelft.context.cg2.client.controller.view.ViewController;
import nl.tudelft.context.cg2.client.model.Lobby;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.Player;
import nl.tudelft.context.cg2.client.view.View;

import java.util.ArrayList;

/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
public class Controller {

    private final ViewController viewController;
    private final GraphicsTimer graphicsTimer;

    private final Model model;
    private final View view;

    /**
     * Constructor for the Controller object.
     * @param model The Model object to send interaction data towards.
     * @param view The View object, containing the UI controls calling back to this controller.
     */
    public Controller(final Model model, final View view) {
        this.viewController = new ViewController(this, view);
        this.graphicsTimer = new GraphicsTimer(view);
        this.model = model;
        this.view = view;
    }

    /**
     * Callback for the CreateGameScene 'Create Game' button listener.
     * Creates the game with the player as host.
     * todo: Communicate game creation with server.
     * @param playerName the player name.
     * @param lobbyName the lobby name.
     * @param password the lobby password.
     */
    public void createGameCallback(String playerName, String lobbyName, String password) {
        Player player = new Player(playerName);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Lobby lobby = new Lobby(lobbyName, password, players, true);
        model.setCurrentPlayer(player);
        model.setCurrentLobby(lobby);
        view.getLobbyScene().setPlayerNames(players);
    }

    /**
     * Callback for the LobbyScene 'Leave' button listener.
     * Leaves the current lobby and forgets current player information.
     * todo: Communicate game leaving with server.
     */
    public void leaveLobbyCallback() {
        model.setCurrentPlayer(null);
        model.setCurrentLobby(null);
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
    public GraphicsTimer getGraphicsTimer() {
        return graphicsTimer;
    }
}
