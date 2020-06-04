package nl.tudelft.context.cg2.client.model.datastructures;

import java.util.ArrayList;

/**
 * A factory for Players.
 */
public class PlayerFactory {
    /**
     * Inaccessible constructor method.
     */
    private PlayerFactory() {}

    private static final ArrayList<Player> PLAYERS = new ArrayList<>();

    /**
     * Creates and returns players.
     * @param name of the player.
     * @return the Player.
     */
    public static Player createPlayer(String name) {
        Player found = null;

        for (Player player: PLAYERS) {
            if (player.getName().equals(name)) {
                found = player;
            }
        }

        if (found != null) {
            return found;
        }
        found = new Player(name);
        PLAYERS.add(found);
        return found;
    }
}
