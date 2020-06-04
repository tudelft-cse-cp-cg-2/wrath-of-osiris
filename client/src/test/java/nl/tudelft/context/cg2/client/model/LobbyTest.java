package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.PlayerFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testUnpackEmptyLobby() {
        ArrayList<Player> players = new ArrayList<>();
        Lobby lobby = new Lobby("test1", "", players, false);
        assertTrue(lobby.equals(Lobby.unpackLobby("0test1")));
    }

    @Test
    public void testUnpackLobbyWithPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(""));
        Lobby lobby = new Lobby("test0", "", players, false);
        assertTrue(lobby.equals(Lobby.unpackLobby("1test0")));
    }

    @Test
    public void testUnpackFetchLobby() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("asdf"));
        Lobby lobby = new Lobby("test1", "", players, false);
        assertTrue(lobby.equals(Lobby.unpackFetchLobby("fetchlobby 1test1 asdf")));
    }
}
