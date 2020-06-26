package nl.tudelft.context.cg2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main server application.
 */
@SuppressWarnings("HideutilityClassConstructor")
public final class Server {

    private static ScheduledExecutorService networkThread;
    private static ScheduledExecutorService gameThread;
    private static final int PORT = 43594;

    private static ArrayList<Lobby> lobbies;
    private static ArrayList<Player> players;

    /**
     * Main function.
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("Starting server...");

        try {
            startServer();
            startNetwork();
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not start server");
        }
    }

    /**
     * Loads the server data and sets variables.
     */
    private static void startServer() {
        networkThread = Executors.newSingleThreadScheduledExecutor();
        gameThread = Executors.newSingleThreadScheduledExecutor();
        lobbies = new ArrayList<>();
        players = new ArrayList<>();
    }

    /**
     * Starts the network.
     */
    private static void startNetwork() throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);

        Runnable ioThread = () -> {
            try {
                while (true) {
                    Socket sock = serverSock.accept();
                    System.out.println("Accepted new connection from " + sock.getInetAddress());

                    Player player = new Player(sock);
                    players.add(player);
                    player.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Game loop error...");
            }
        };

        networkThread.submit(ioThread);
    }

    /**
     * Starts the game.
     */
    private static void startGame() {
        Runnable mainThread = () -> {
            try {
                players.forEach(Player::process);
                lobbies.forEach(Lobby::process);
                players.removeIf(Player::hasDisconnected);
                lobbies.removeIf(Lobby::isEmpty);

                System.out.println("Player #: " + players.size());
                System.out.println("Lobby #: " + lobbies.size());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Gane loop error...");
            }
        };

        gameThread.scheduleAtFixedRate(mainThread, 0, 600, TimeUnit.MILLISECONDS);
    }

    /**
     * Adds a player to a specific lobby.
     * @param lobbyName the name of the lobby
     * @param player the player object
     */
    public static void addPlayerToLobby(String lobbyName, Player player) {
        Lobby lobby = getLobbyByName(lobbies, lobbyName);
        if (lobby == null || lobby.isFull()) {
            System.out.println("Lobby not available.");
        } else {
            player.joinLobby(lobby);
        }
    }

    /**
     * Fetches a lobby for the client.
     * @param lobbyName the lobby to be fetched
     * @return the packed lobby and its player list
     */
    public static String fetchLobby(String lobbyName) {
        String out = "fetchlobby ";
        Lobby lobby = getLobbyByName(lobbies, lobbyName);
        if (lobby == null) {
            System.out.println("No such lobby");
        } else {
            out += lobby.pack();
        }

        return out;
    }

    /**
     * Create a new lobby.
     * @param player the player creating the lobby, who will also automatically join
     * @param lobbyName the name of the lobby
     * @param password the optional password of the lobby, or if none, null
     * @return the newly created lobby
     */
    public static Lobby createLobby(Player player, String lobbyName, String password) {
        Lobby lobby = new Lobby(lobbyName, password, new ArrayList<>());
        lobbies.add(lobby);
        player.joinLobby(lobby);
        return lobby;
    }

    /**
     * Create a new lobby without a password.
     * @param player the player creating the lobby, who will also automatically join
     * @param lobbyName the name of the lobby
     * @return the newly created lobby
     */
    public static Lobby createLobby(Player player, String lobbyName) {
        return createLobby(player, lobbyName, null);
    }

    /**
     * Generate a compact representation of all existing lobbies.
     * @return a list of string representing all lobbies
     */
    public static List<String> packLobbies() {
        List<String> out = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            if (!lobby.inGame()) {
                out.add(lobby.getPlayers().size() + lobby.getName());
            }
        }
        return out;
    }

    /**
     * Checks whether or not a given player name is already in use.
     * @param playerName the player name to check
     * @return true if the name is unique, else false
     */
    public static boolean playerNameIsUnique(String playerName) {
        for (Lobby lobby : lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getPlayerName().equals(playerName)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether or not a lobby player name is already in use.
     * @param lobbyName the player name to check
     * @return true if the name is unique, else false
     */
    public static boolean lobbyNameIsUnique(String lobbyName) {
        for (Lobby lobby : lobbies) {
            if (lobby.getName().equals(lobbyName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A getter for the lobby by name.
     * @param lobbies array of lobbies
     * @param lobbyName name of the lobby
     * @return the lobby, or null if not found.
     */
    private static Lobby getLobbyByName(ArrayList<Lobby> lobbies, String lobbyName) {
        for (Lobby lobby : lobbies) {
            if (lobbyName.equals(lobby.getName())) {
                return lobby;
            }
        }
        return null;
    }
}
