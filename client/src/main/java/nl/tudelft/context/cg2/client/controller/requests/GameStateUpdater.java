package nl.tudelft.context.cg2.client.controller.requests;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimerTask;

/**
 * Updater class that syncs the group lives and poses with the server.
 */
public class GameStateUpdater extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private final int index;

    private boolean terminate = false;

    /**
     * Constructor for GameStateUpdater.
     * @param in server input
     * @param out server output
     * @param index the lobby to fetch
     */
    public GameStateUpdater(BufferedReader in, PrintWriter out, int index) {
        this.in = in;
        this.out = out;
        this.index = index;
    }

    /**
     * Changes this.terminate to true, effectively telling the run function to terminate.
     */
    public void terminate() {
        this.terminate = true;
    }

    /**
     * Executes the updater.
     */
    public void run() {
        String serverInput;
        try {
            while (!terminate) {
                serverInput = in.readLine();
                if (serverInput == null) {
                    break;
                }
                System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> "
                        + serverInput);
                respond(serverInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
