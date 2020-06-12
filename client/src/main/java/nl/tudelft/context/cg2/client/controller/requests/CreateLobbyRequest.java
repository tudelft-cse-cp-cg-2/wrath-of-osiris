package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.controller.controllers.NetworkController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request for joining a lobby on the server.
 */
public class CreateLobbyRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;

    private final String playerName;
    private final String lobbyName;
    private final String password;

    private boolean success;

    /**
     * Getter for resultIndex.
     * @return the lobby that was just joined
     */
    public boolean isSuccessful() {
        return success;
    }

    /**
     * Constructor for JoinLobbyRequest.
     * @param in server input
     * @param out server output
     * @param playerName the name the player would like to use
     * @param lobbyName the name to call the lobby
     * @param password the optional password for the lobby
     */
    public CreateLobbyRequest(BufferedReader in, PrintWriter out, String playerName,
                              String lobbyName, String password) {
        this.out = out;
        this.in = in;
        this.playerName = playerName;
        this.lobbyName = lobbyName;
        this.password = password;
    }

    /**
     * Executes the request.
     */
    public void run() {
        String fromServer;

        try {
            // create lobby
            if (password != null) {
                out.println("createlobby " + playerName + " " + lobbyName + " " + password);
            } else {
                out.println("createlobby " + playerName + " " + lobbyName);
            }
            fromServer = in.readLine();
            assert fromServer != null;
            System.out.println(fromServer);
            success = !fromServer.equals(NetworkController.EOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
