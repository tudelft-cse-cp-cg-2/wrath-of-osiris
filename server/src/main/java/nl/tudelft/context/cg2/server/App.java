package nl.tudelft.context.cg2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Main server application.
 */
@SuppressWarnings("HideutilityClassConstructor")
public final class App {
    private static final int PORT = 43594;

    private static ArrayList<Lobby> lobbies;

    /**
     * Starts the server.
     * @throws IOException when no socket could be opened
     */
    private static void startServer() throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);
        lobbies = new ArrayList<>();

        System.out.println("Started server on port " + PORT);
        while (true) {
            Socket sock = serverSock.accept();
            System.out.println("Accepted new connection from " + sock.getInetAddress());

            Player player = new Player(sock);
            player.start();
        }
    }

    /**
     * Terminate a player's thread, effectively disconnecting them if they were still connected.
     * @param player the player to interrupt
     */
    public static void disconnectPlayer(Player player) {
        // remove the player from the lobby
        for (Lobby lobby : lobbies) {
            for (Player p : lobby.getPlayers()) {
                if (p.getPlayerName().equals(player.getPlayerName())) {
                    lobby.removePlayer(p);
                    break;
                }
            }
        }

        // tell player class to stop its main loop
        player.terminate();

        // interrupt the player's thread
        player.interrupt();
    }

    /**
     * Removes a player from all lobbies.
     * @param player the player to be removed
     */
    public static void removePlayerFromLobbies(Player player) {
        lobbies.forEach(x -> x.removePlayer(player));
        player.setLobby(null);
    }

    /**
     * Adds a player to a specific lobby.
     * @param index the index of the lobby
     * @param player the player object
     */
    public static void addPlayerToLobby(int index, Player player) {
        if (index < 0 || index >= lobbies.size()) {
            System.out.println("No such lobby");
        } else {
            Lobby lobby = lobbies.get(index);
            if (lobby.isFull()) {
                disconnectPlayer(player);
            } else {
                lobby.addPlayer(player);
                player.setLobby(lobby);
            }
        }
    }

    /**
     * Fetches a lobby for the client.
     * @param index the lobby to be fetched
     * @return the packed lobby and its player list
     */
    public static String fetchLobby(int index) {
        String out = "fetchlobby ";

        if (index < 0 || index >= lobbies.size()) {
            System.out.println("No such lobby");
        } else {
            Lobby lobby = lobbies.get(index);
            out += lobby.pack();
        }

        return out;
    }

    /**
     * Create a new lobby.
     * @param player the player creating the lobby, who will also automatically join
     * @param lobbyName the name of the lobby
     * @param password the optional password of the lobby, or if none, null
     * @return the index of the newly created lobby
     */
    public static int createLobby(Player player, String lobbyName, String password) {
        Lobby lobby = new Lobby(lobbyName, password, new ArrayList<Player>());
        lobby.addPlayer(player);
        lobbies.add(lobby);
        return lobbies.indexOf(lobby);
    }

    /**
     * Create a new lobby without a password.
     * @param player the player creating the lobby, who will also automatically join
     * @param lobbyName the name of the lobby
     * @return the index of the newly created lobby
     */
    public static int createLobby(Player player, String lobbyName) {
        return createLobby(player, lobbyName, null);
    }


    /**
     * Generate a compact representation of all existing lobbies.
     * @return a list of string representing all lobbies
     */
    public static List<String> packLobbies() {
        List<String> out = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            if (!lobby.isStarted()) {
                out.add(lobby.getPlayers().size() + lobby.getName());
            }
        }
        return out;
    }

    /**
     * Main function.
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not start server");
        }
    }
}
