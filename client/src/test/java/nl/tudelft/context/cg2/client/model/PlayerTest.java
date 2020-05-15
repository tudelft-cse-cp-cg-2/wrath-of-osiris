package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the client Player class.
 */
public class PlayerTest {

    /**
     * Test constructor.
     */
    @Test
    public void testConstructor() {
        Player player = new Player("Name");
        assertEquals("Name", player.getName());
    }
}
