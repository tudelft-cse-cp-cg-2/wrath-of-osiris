package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.BackendWall;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;

import java.util.ArrayList;

/**
 * The model class.
 * Holds all the client data and data structures.
 */
public class Model {

    private final World world;
    private ArrayList<Lobby> availableLobbies;
    private Lobby currentLobby = null;
    private Player currentPlayer = null;
    private int lives = -1;
    private ArrayList<BackendWall> currentLevel = null;

    /**
     * The model constructor.
     */
    public Model() {
        this.world = new World();
    }

    /**
     * Loads the client data from external resources.
     */
    public void load() {
        ImageCache.loadImages();
        world.create();
    }

    /**
     * World getter.
     * @return the game world.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Getter for current lobby object.
     * @return the currently participating lobby.
     */
    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    /**
     * Setter method for current lobby.
     * @param currentLobby the currently participating lobby.
     */
    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    /**
     * Getter for current player.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter for current player.
     * @param currentPlayer the current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter for amount of lives left.
     * @return amount of lives left
     */
    public int getLives() {
        return lives;
    }

    /**
     * Setter to update lives.
     * @param lives new amount of lives left
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Getter for the current level.
     * @return level
     */
    public ArrayList<BackendWall> getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Setter to update the current level.
     * @param currentLevel level
     */
    public void setCurrentLevel(ArrayList<BackendWall> currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Getter for local set of available lobbies in lobby list.
     * @return local set of currently available lobbies
     */
    public ArrayList<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }

    /**
     * Setter to update local set of available lobbies in lobby list.
     * @param availableLobbies new set of currently available lobbies
     */
    public void setAvailableLobbies(ArrayList<Lobby> availableLobbies) {
        this.availableLobbies = availableLobbies;
    }
}
