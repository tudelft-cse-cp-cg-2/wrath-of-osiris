package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.Settings;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.files.SoundCache;
import nl.tudelft.context.cg2.client.model.world.World;

import java.util.ArrayList;

/**
 * The model class.
 * Holds all the client data and data structures.
 */
public class Model {

    private World world;
    private ArrayList<Lobby> availableLobbies;
    private Lobby currentLobby = null;
    private Player currentPlayer = null;

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
        Settings.load();
        ImageCache.loadImages();
        SoundCache.loadSounds();
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
