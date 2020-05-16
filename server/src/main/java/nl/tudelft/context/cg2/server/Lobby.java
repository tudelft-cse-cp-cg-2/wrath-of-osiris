package nl.tudelft.context.cg2.server;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;

/**
 * Class containing information about the lobby a player is currently in.
 */
public class Lobby {

    private final String name;
    private final String password;

    /**
     * The list of connected players. The first one (index 0) is always the host.
     */
    private final ArrayList<Player> players;

    /**
     * Constructor for the Lobby.
     * When the host creates a Lobby, only the host is added as player.
     * If a player joins a lobby, 'players' contains all players, including himself.
     * @param name lobby name.
     * @param password lobby password.
     * @param players list of current players in the lobby.
     */
    public Lobby(String name, String password, ArrayList<Player> players) {
        this.name = name;
        this.password = password;
        this.players = players;
    }

    /**
     * Setter method to adjust current players in the lobby.
     * @param player the new set of players in the lobby.
     */
    public void addPlayer(@NonNull Player player) {
        this.players.add(player);
    }

    /**
     * Getter method for current players in the lobby.
     * @return current list of players in the lobby.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for lobby name.
     * @return the lobby name.
     */
    public String getName() {
        return name;
    }

    /**
     * Pack to send over the Internet.
     * @return a packed string representing this lobby
     */
    public String pack() {
        return players.size() + name;
    }

    /**
     * Removes a player from the lobby.
     * @param player player to be removed
     */
    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
