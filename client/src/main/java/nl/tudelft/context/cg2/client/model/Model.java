package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;

/**
 * The model class.
 * Holds all the client data and data structures.
 */
public class Model {

    private final World world;
    private Lobby currentLobby = null;
    private Player currentPlayer = null;
    private int lives = -1;

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
}
