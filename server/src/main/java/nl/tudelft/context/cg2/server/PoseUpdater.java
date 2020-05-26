package nl.tudelft.context.cg2.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Updater class that periodically updates other player's teammates'
 * poses to the current player.
 * Because player pose is only communicated to see others, and not for game
 * mechanics, it can be periodically sent.
 */
public class PoseUpdater extends TimerTask {
    private final BufferedReader in;
    private final PrintWriter out;
    private final Player player;

    /**
     * Constructor for PoseUpdater.
     * @param in client input
     * @param out client output
     * @param player current player
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
        ArrayList<Player> players = new ArrayList<>();
        try {
            players = player.getLobby().getPlayers();
        } catch (NullPointerException e) {
            System.out.println("No players available");
        }


        for (Player otherPlayer : players) {
            if (!player.getPlayerName().equals(otherPlayer.getPlayerName())) {
                out.println("updatepose " + otherPlayer.getPlayerName() + " "
                        + otherPlayer.getPose().pack());
                System.out.println("Updated " + otherPlayer.getPlayerName() + " of other poses");
            }
        }
    }
}
