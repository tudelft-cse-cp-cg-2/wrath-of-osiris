package nl.tudelft.context.cg2.client.controller.requests;

import nl.tudelft.context.cg2.client.Settings;
import nl.tudelft.context.cg2.client.model.datastructures.Player;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.TimerTask;

/**
 * Updater class that periodically updates player's pose to server.
 * Because player pose is only communicated to see others, and not for game
 * mechanics, it can be periodically sent.
 */
public class PoseUpdater extends TimerTask {
    private final BufferedReader in;
    private final PrintWriter out;
    private final Player player;

    /**
     * Constructor for PoseUpdater.
     * @param in server input
     * @param out server output
     * @param player player
     */
    public PoseUpdater(BufferedReader in, PrintWriter out, Player player) {
        this.in = in;
        this.out = out;
        this.player = player;
    }

    /**
     * Executes the request.
     */
    public void run() {
        Settings.debugMessage("Updated pose to server");
        out.println("updatepose " + player.getPose().pack());
    }
}
