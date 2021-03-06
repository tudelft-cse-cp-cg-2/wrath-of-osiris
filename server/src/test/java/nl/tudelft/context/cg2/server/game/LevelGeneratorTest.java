package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LevelGeneratorTest {

    @Test
    public void testNoNumbersOnFirstLevel() {
        LevelGenerator gen = new LevelGenerator(4);
        ArrayList<Wall> level = gen.generateLevel();
        for (Wall w : level) {
            for (ScreenPos s : ScreenPos.values()) {
                assertEquals(-1, w.getNumber(s));
            }
        }
    }

    @Test
    void testNumbersDoNotExceedPlayerAmount() {
        LevelGenerator gen = new LevelGenerator(4);
        for (int i = 0; i < 10; i++) {
            gen.generateLevel();
        }
        ArrayList<Wall> level = gen.generateLevel();
        for (Wall w : level) {
            for (ScreenPos s : ScreenPos.values()) {
                if (w.getNumber(s) != null) {
                    assertTrue(w.getNumber(s) <= 4);
                }
            }
        }
    }

    @Test
    void levelFromAndToJSON() {
        LevelGenerator gen = new LevelGenerator(4);
        ArrayList<Wall> level = gen.generateLevel();
        String res = LevelGenerator.levelToJsonString(level);
        System.out.println(res);
        LevelGenerator.jsonStringToLevel(res);
        assertEquals(level, LevelGenerator.jsonStringToLevel(res));
    }
}
