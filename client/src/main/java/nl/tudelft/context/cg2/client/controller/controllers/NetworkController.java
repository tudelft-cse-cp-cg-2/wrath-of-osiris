package nl.tudelft.context.cg2.client.controller.controllers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Representation of the game server.
 */
public class NetworkController {
    private static final String HOST = "localhost";
    private static final int PORT = 43594;

    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Sequence sent at End Of Transmission.
     */
    public static final String EOT = ".";

    /**
     * Gets out.
     * @return out.
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * Gets in.
     * @return in.
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Constructor for servers.
     */
    public NetworkController() {

    }

    /**
     * Connect to the server.
     * @return true if the connection was successful, false otherwise.
     */
    @SuppressFBWarnings(value = "DM_EXIT", justification = "If we cannot connect, we cannot play.")
    public boolean connect() {
        try {
            this.sock = new Socket(HOST, PORT);
            this.out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(),
                    StandardCharsets.UTF_8), true);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream(),
                    StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            System.out.println("Error: could not connect to server.");
        }
        return false;
    }
}
