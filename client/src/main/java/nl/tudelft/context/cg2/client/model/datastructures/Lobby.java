package nl.tudelft.context.cg2.client.model.datastructures;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing information about a lobby.
 * To be used both in the menus and the game.
 */
public class Lobby {

    private final String name;
    private final String password;
    private List<Player> players;
    private final Boolean isHost;

    /**
     * Constructor for the Lobby.
     * When the host creates a Lobby, only the host is added as player.
     * If a player joins a lobby, 'players' contains all players, including himself.
     * @param name lobby name.
     * @param password lobby password.
     * @param players list of current players in the lobby.
     * @param isHost whether currentPlayer is host of the lobby.6
     */
    public Lobby(String name, String password, List<Player> players, Boolean isHost) {
        this.name = name;
        this.password = password;
        this.players = players;
        this.isHost = isHost;
    }

    /**
     * Create a lobby from a packed string sent by the server.
     * @param packed lobby representation from the server
     * @return a Lobby
     */
    public static Lobby unpackLobby(String packed) {
        ArrayList<Player> playerList = new ArrayList<>();
        int playerCount = packed.charAt(0) - '0';
        for (int i = 0; i < playerCount; i++) {
            playerList.add(new Player(""));
        }
        return new Lobby(packed.substring(1), "", playerList, false);
    }

    /**
     * Setter method to adjust current players in the lobby.
     * @param players the new set of players in the lobby.
     */
    public void setPlayers(@NonNull ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Add a player to the lobby.
     * @param player the player to add
     */
    public void addPlayer(@NonNull Player player) {
        this.players.add(player);
    }

    /**
     * Getter method for current players in the lobby.
     * @return current list of players in the lobby.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets a list of player names for the players in the lobby.
     * @return list of player names.
     */
    public List<String> getPlayerNames() {
        return players.stream().map(Player::getName).collect(Collectors.toList());
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
