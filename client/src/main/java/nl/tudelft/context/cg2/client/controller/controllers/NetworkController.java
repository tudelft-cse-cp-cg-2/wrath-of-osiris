package nl.tudelft.context.cg2.client.controller.controllers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.context.cg2.client.Settings;

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
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Sequence sent at End Of Transmission.
     */
    public static final String EOT = ".";

    /**
     * Disconnect the client from the server.
     */
    public void disconnect() {
        if (sock != null && sock.isConnected()) {
            out.println("disconnect");

            try {
                out.close();
                in.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
     * Connect to the server.
     * @return true if the connection was successful, false otherwise.
     */
    @SuppressFBWarnings(value = "DM_EXIT", justification = "If we cannot connect, we cannot play.")
    public boolean connect() {
        try {
            this.sock = new Socket(Settings.getServerIp(), Settings.getServerPort());
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
