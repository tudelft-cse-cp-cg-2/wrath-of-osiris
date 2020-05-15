package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the client Lobby class.
 */
public class LobbyTest {

    /**
     * Tests constructor method.
     */
    @Test
    public void testConstructor() {
        ArrayList<Player> players = new ArrayList<>();
        Lobby lobby = new Lobby("a", "b", players, true);
        assertEquals("a", lobby.getName());
        assertEquals(players, lobby.getPlayers());
        assertEquals(true, lobby.getHost());
    }

    /**
     * Test setPlayers method.
     */
    @Test
    public void testSetPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> players1 = new ArrayList<>();
        Lobby lobby = new Lobby("a", "b", players, true);
        lobby.setPlayers(players1);
        assertEquals(players1, lobby.getPlayers());
    }
}
