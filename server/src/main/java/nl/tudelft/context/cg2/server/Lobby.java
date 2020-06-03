package nl.tudelft.context.cg2.server;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;

/**
 * Class containing information about the lobby a player is currently in.
 */
public class Lobby {
    private static final int MAX_PLAYERS = 5;

    private final String name;
    private final String password;
    private boolean started = false;
    private int lives = 10;

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
        StringBuilder out = new StringBuilder(players.size() + name);
        for (Player player : players) {
            out.append(" ").append(player.getPlayerName());
        }
        return out.toString();
    }

    /**
     * Removes a player from the lobby.
     * @param player player to be removed
     */
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    /**
     * Returns whether or not the lobby is full.
     * @return true if full, false if not
     */
    public boolean isFull() {
        return (this.players.size() >= MAX_PLAYERS);
    }

    /**
     * Getter for the group's amount of lives left.
     * @return the amount of lives the group has left
     */
    public int getLives() {
        return lives;
    }

    /**
     * Setter for the group's amount of lives left.
     * @param lives the updated amount of lives left
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets whether the lobby has started the game.
     * @return boolean whether the game has been started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Starts the game for the lobby, and update all players'
     * lives to the starting amount.
     */
    public void startGame() {
        if (!started) {
            this.started = true;
            for (Player player : players) {
                player.startPoseUpdater();
                player.startGame();
                player.updateLives();
            }
        }
    }

    /**
     * Stops the game for the lobby.
     */
    public void stopGame() {
        this.started = false;
        for (Player player : players) {
            player.stopGame();
        }
    }
}
