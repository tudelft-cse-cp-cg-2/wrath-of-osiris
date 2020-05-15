package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.controller.core.GameTimer;
import nl.tudelft.context.cg2.client.controller.view.ViewController;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.view.View;

import java.util.ArrayList;

/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
public class Controller {

    private final ViewController viewController;
    private final GameTimer gameTimer;

    private final Model model;
    private final View view;

    // todo: Use this field to maintain available lobbies.
//    private ArrayList<Lobby> lobbies;

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
    }

    /**
     * Callback for main menu scene 'Join Game' button listener.
     * Retrieves available lobbies from server and loads them into the scene.
     * todo: Retrieve lobbies from server.
     */
    public void lobbyListCallback() {
        // Example lobby.
        ArrayList<Player> exampleList = new ArrayList<Player>();
        exampleList.add(new Player("host"));
        Lobby exampleLobby = new Lobby("ExampleLobby", null, exampleList, false);
        ArrayList<Lobby> lobbies = new ArrayList<>();
        lobbies.add(exampleLobby);
        view.getJoinScene().setLobbyNames(lobbies);
        view.getJoinScene().show();
    }

    /**
     * Callback for the joinScene 'Join Game' button listener.
     * Joins the game with the player as guest.
     * It is assumed that list 'lobbies' corresponds to JoinScene's 'lobbyEntries'.
     * todo: Communicate game joining with server.
     * @param playerName the player name.
     * @param selectedLobby the index of the selected lobby.
     */
    public void joinGameCallback(String playerName, int selectedLobby) {
        Player player = new Player(playerName);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        // Retrieve and store lobby data from server.
        view.getLobbyScene().setPlayerNames(players);
        view.getLobbyScene().getStartButton().setVisible(false);
        view.getLobbyScene().getWaitMessage().setVisible(true);
        view.getLobbyScene().show();
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
        view.getLobbyScene().getStartButton().setVisible(true);

        view.getLobbyScene().getWaitMessage().setVisible(false);
        view.getLobbyScene().show();
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
    public GameTimer getGameTimer() {
        return gameTimer;
    }
}
