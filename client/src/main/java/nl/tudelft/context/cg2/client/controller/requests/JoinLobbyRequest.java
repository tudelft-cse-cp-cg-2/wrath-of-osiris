package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.controller.controllers.NetworkController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request for joining a lobby on the server.
 */
public class JoinLobbyRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private final String lobbyName;
    private final String playerName;

    private boolean success;

    /**
     * Getter for result.
     * @return the name of the lobby that was just joined
     */
    public boolean isSuccessful() {
        return success;
    }

    /**
     * Constructor for JoinLobbyRequest.
     * @param in server input
     * @param out server output
     * @param lobby the name of the lobby to be joined
     * @param player the name the player would like to use
     */
    public JoinLobbyRequest(BufferedReader in, PrintWriter out, String lobby, String player) {
        this.out = out;
        this.in = in;
        this.lobbyName = lobby;
        this.playerName = player;
    }

    /**
     * Executes the request.
     */
    public void run() {
        String fromServer;
        try {
            out.println("joinlobby " + lobbyName + " " + playerName);
            fromServer = in.readLine();
            assert fromServer != null;
            success = !fromServer.equals(NetworkController.EOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
