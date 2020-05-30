package nl.tudelft.context.cg2.client.controller.requests;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.TimerTask;

/**
 * Request for fetching a lobby's information from the server.
 */
public class FetchLobbyRequest extends TimerTask {
    private final BufferedReader in;
    private final PrintWriter out;
    private String lobbyName;

    /**
     * Constructor for FetchLobbyRequest.
     * @param in server input
     * @param out server output
     * @param lobbyName the name of the lobby to fetch
     */
    public FetchLobbyRequest(BufferedReader in, PrintWriter out, String lobbyName) {
        this.in = in;
        this.out = out;
        this.lobbyName = lobbyName;
    }

    /**
     * Executes the request.
     */
    public void run() {
        out.println("fetchlobby " + lobbyName);
    }
}
