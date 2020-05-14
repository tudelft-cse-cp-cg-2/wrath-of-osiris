package nl.tudelft.context.cg2.client.model;

/**
 * Contains local information about a player.
 */
public class Player {
    private final String name;

    /**
     * Constructor for the Player.
     * @param name the player's name.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter for a player's name.
     * @return the player name.
     */
    public String getName() {
        return name;
    }
}
