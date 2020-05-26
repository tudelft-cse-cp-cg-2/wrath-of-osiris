package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.TimerTask;

/**
 * Request for fetching a lobby's information from the server.
 */
public class FetchLobbyRequest extends TimerTask {
    private final BufferedReader in;
    private final PrintWriter out;
    private int index;

    private Lobby result;

    /**
     * Getter for result.
     * @return the requested lobby
     */
    public Lobby getResult() {
        return result;
    }

    /**
     * Constructor for FetchLobbyRequest.
     * @param in server input
     * @param out server output
     * @param index the index of the lobby to fetch
     */
    public FetchLobbyRequest(BufferedReader in, PrintWriter out, int index) {
        this.in = in;
        this.out = out;
        this.index = index;
    }

    /**
     * Executes the request.
     */
    public void run() {
        String fromServer;

        if (index == -1) {
            index = 0;
        }

        out.println("fetchlobby " + index);
    }
}
