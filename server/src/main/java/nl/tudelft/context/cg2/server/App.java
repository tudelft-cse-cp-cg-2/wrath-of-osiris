package nl.tudelft.context.cg2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Main server application.
 */
public final class App {
    private static final int PORT = 43594;

    private static ArrayList<Lobby> lobbies;

    /**
     * Private constructor, since this is a utility class that should not be instantiated.
     */
    private App() {}

    /**
     * Starts the server.
     * @throws IOException when no socket could be opened
     */
    private static void startServer() throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);
        lobbies = new ArrayList<>();

        // TODO: remove these lines
        lobbies.add(new Lobby("test", "", new ArrayList<>()));
        lobbies.get(0).addPlayer(new Player("jan"));
        lobbies.get(0).addPlayer(new Player("piet"));
        lobbies.add(new Lobby("one", "", new ArrayList<>()));

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
        player.interrupt();
    }

    /**
     * Removes a player from all lobbies.
     * @param player the player to be removed
     */
    public static void removePlayerFromLobbies(Player player) {
        lobbies.forEach(x -> x.removePlayer(player));
    }

    /**
     * Adds a player to a specific lobby.
     * @param index the index of the lobby
     * @param player the player object
     * @return the response to the player
     */
    public static List<String> addPlayerToLobby(int index, Player player) {
        List<String> out = new ArrayList<>();

        if (index < 0 || index >= lobbies.size()) {
            System.out.println("No such lobby");
        } else {
            Lobby lobby = lobbies.get(index);
            lobby.addPlayer(player);
            out.add(lobby.pack());
            lobby.getPlayers().forEach(x -> out.add(x.getPlayerName()));

            // TODO: remove these lines
            lobby.getPlayers().forEach(x -> System.out.println(x.getPlayerName()));
        }

        return out;
    }

    /**
     * Generate a compact representation of all existing lobbies.
     * @return a list of string representing all lobbies
     */
    public static List<String> packLobbies() {
        List<String> out = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            out.add(lobby.pack());
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
