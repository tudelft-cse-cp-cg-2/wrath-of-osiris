package nl.tudelft.context.cg2.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
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
    private final Lobby lobby;
    private final String currentPlayerName;

    /**
     * Constructor for PoseUpdater.
     * @param in client input
     * @param out client output
     * @param lobby lobby
     * @param currentPlayerName the name of the player not to be updated
     */
    public PoseUpdater(BufferedReader in, PrintWriter out, Lobby lobby, String currentPlayerName) {
        this.in = in;
        this.out = out;
        this.lobby = lobby;
        this.currentPlayerName = currentPlayerName;
    }

    /**
     * Executes the request.
     */
    public void run() {
        for (Player player : lobby.getPlayers()) {
            if (!currentPlayerName.equals(player.getPlayerName())) {
                out.println("updatepose " + player.getPlayerName() + " " + player.getPose().pack());
            }
        }
    }
}
