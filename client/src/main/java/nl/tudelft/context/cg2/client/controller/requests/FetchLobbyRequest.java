package nl.tudelft.context.cg2.client.controller.requests;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request for fetching a lobby's information from the server.
 */
public class FetchLobbyRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private int index;

    private static Lobby result;

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
        try {
            // get lobby
            fromServer = in.readLine();
            if (fromServer == null) {
                Platform.exit();
            }
            System.out.println(fromServer);
            result = Lobby.unpackLobby(fromServer);

            System.out.println("fetchlobby: " + fromServer);

            // get players
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null || fromServer.equals(Server.EOT)) {
                    break;
                }
                System.out.println("player: " + fromServer);
                result.addPlayer(new Player(fromServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
