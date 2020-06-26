package nl.tudelft.context.cg2.server;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class containing information about the lobby a player is currently in.
 */
public class Lobby {
    private static final int MAX_PLAYERS = 5;

    private Game game;
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
     * @param name     lobby name.
     * @param password lobby password.
     * @param players  list of current players in the lobby.
     */
    public Lobby(String name, String password, ArrayList<Player> players) {
        this.name = name;
        this.password = password;
        this.players = players;
        this.game = null;
    }

    public void process() {
        if (game != null) {
            game.process();
        }
    }

    /**
     * Starts the game for the lobby, and update all players'
     * lives to the starting amount.
     */
    public void startGame() {
        if (game == null) {
            game = new Game(this);
            players.forEach(Player::joinGame);
            System.out.println("Starting game!");
        }
    }

    /**
     * Stops the game for the lobby.
     */
    public void stopGame() {
        players.forEach(Player::leaveGame);
        game = null;
    }

    /**
     * Setter method to adjust current players in the lobby.
     * @param player the new set of players in the lobby.
     */
    public void addPlayer(@NonNull Player player) {
        this.players.add(player);
    }

    /**
     * Pack to send over the Internet.
     * @return a packed string representing this lobby
     */
    public String pack() {
        StringBuilder builder = new StringBuilder(players.size() + name);
        for (Player player : players) {
            builder.append(" ").append(player.getPlayerName());
        }
        return builder.toString();
    }

    /**
     * Returns whether or not the lobby is full.
     * @return true if full, false if not
     */
    public boolean isFull() {
        return players.size() >= MAX_PLAYERS;
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

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public void leave(Player player) {
        if (players.remove(player)) {
            players.forEach(p -> p.sendPlayerLeft(player.getPlayerName()));
        }
    }

    public Game getGame() {
        return game;
    }

    public boolean inGame() {
        return game != null;
    }
}
