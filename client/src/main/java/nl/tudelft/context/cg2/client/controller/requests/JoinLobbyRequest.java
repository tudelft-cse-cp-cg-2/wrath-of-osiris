package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request for joining a lobby on the server.
 */
public class JoinLobbyRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private int index;
    private final String playerName;

    private Lobby result;

    /**
     * Getter for result.
     * @return the lobby that was just joined
     */
    public Lobby getResult() {
        return result;
    }

    /**
     * Constructor for JoinLobbyRequest.
     * @param in server input
     * @param out server output
     * @param index the index of the lobby to be joined
     * @param playerName the name the player would like to use
     */
    public JoinLobbyRequest(BufferedReader in, PrintWriter out, int index, String playerName) {
        this.out = out;
        this.in = in;
        this.index = index;
        this.playerName = playerName;
    }

    /**
     * Executes the request.
     */
    public void run() {
        String fromServer;

        if (index == -1) {
            index = 0;
        }

        try {
            // join lobby
            out.println("joinlobby " + index + " " + playerName);
            fromServer = in.readLine();
            assert fromServer != null && fromServer.equals(Server.EOT);

            FetchLobbyRequest req = new FetchLobbyRequest(in, out, index);
//            req.start();
//            req.join();
//            result = req.getResult();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
