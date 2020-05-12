package nl.tudelft.context.cg2.client.model;

import java.util.ArrayList;

/**
 * Class containing information about the lobby a player is currently in.
 */
public class Lobby {

    private final String name;
    private final String password;
    private ArrayList<Player> players;
    private final Boolean isHost;

    /**
     * Constructor for the Lobby.
     * When the host creates a Lobby, only the host is added as player.
     * If a player joins a lobby, 'players' contains all players, including himself.
     * @param name lobby name.
     * @param players list of current players in the lobby.
     */
    public Lobby(String name, String password, ArrayList<Player> players, Boolean isHost) {
        this.name = name;
        this.password = password;
        this.players = players;
        this.isHost = isHost;
    }

    /**
     * Setter method to adjust current players in the lobby.
     * @param players the new set of players in the lobby.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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
     * Checks whether the current player is host of current lobby.
     * @return boolean whether the current player is host of current lobby.
     */
    public Boolean getHost() {
        return isHost;
    }
}
