package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.controller.controllers.NetworkController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request for leaving the current lobby.
 */
public class LeaveLobbyRequest extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;

    /**
     * Constructor for LeaveLobbyRequest.
     * @param in server input
     * @param out server output
     */
    public LeaveLobbyRequest(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Executes the request.
     */
    public void run() {
        out.println("disconnect");
        try {
            String fromServer = in.readLine();
            assert fromServer != null && fromServer.equals(NetworkController.EOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
