package nl.tudelft.context.cg2.client.model;

/**
 * The model class.
 * Holds all the client data and data structures.
 */
public class Model {

    private Lobby currentLobby = null;
    private Player currentPlayer = null;

    /**
     * Loads the client data.
     */
    public void loadData() {
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
