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
        lobbies.add(new Lobby("test1", null, new ArrayList<>()));

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
        player.terminate();
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
    public static List<String> fetchLobby(int index) {
        List<String> out = new ArrayList<>();

        if (index < 0 || index >= lobbies.size()) {
            System.out.println("No such lobby");
        } else {
            Lobby lobby = lobbies.get(index);
            out.add(lobby.pack());
            lobby.getPlayers().forEach(x -> out.add(x.getPlayerName()));
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
