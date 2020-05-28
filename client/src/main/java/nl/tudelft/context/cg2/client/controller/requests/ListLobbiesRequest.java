package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.NetworkController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Request for retrieving a list of lobbies from the server.
 */
public class ListLobbiesRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;

    private final ArrayList<Lobby> result = new ArrayList<>();

    /**
     * Getter for result.
     * @return a list of lobbies
     */
    public ArrayList<Lobby> getResult() {
        return result;
    }

    /**
     * Constructor for ListLobbiesRequest.
     * @param in server input
     * @param out server output
     */
    public ListLobbiesRequest(BufferedReader in, PrintWriter out) {
        this.out = out;
        this.in = in;
    }

    /**
     * Executes the request.
     */
    public void run() {
        String fromServer;

        out.println("listlobbies");
        try {
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null || fromServer.equals(NetworkController.EOT)) {
                    break;
                }
                System.out.println("lobby: " + fromServer);
                result.add(Lobby.unpackLobby(fromServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
