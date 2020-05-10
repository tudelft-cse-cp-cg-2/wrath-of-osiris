package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LevelGeneratorTest {
    @Test
    void randZero() {
        LevelGenerator l = new LevelGenerator(4);
        assertEquals(l.rand(0), 0);
    }

    @Test
    void randRange() {
        LevelGenerator l = new LevelGenerator(4);
        assertTrue(l.rand(2) >= 0 && l.rand(2) <= 2);
    }
}
