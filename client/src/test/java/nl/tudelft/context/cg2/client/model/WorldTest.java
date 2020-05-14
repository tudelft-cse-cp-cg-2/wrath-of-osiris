package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {

    @Test
    void testEmptyConstructor() {
        World w = new World();

        assertTrue(w.getEntities().isEmpty());
        assertFalse(w.isInMotion());
    }

    @Test
    void testSetInMotion() {
        World w = new World();
        assertFalse(w.isInMotion());

        w.setInMotion(true);
        assertTrue(w.isInMotion());
    }

    @Test
    void testWorldDimensions() {
        World w = new World();
        Vector3D v = w.getDimensions();

        assertEquals(World.WIDTH, v.x);
        assertEquals(World.HEIGHT, v.y);
        assertEquals(World.DEPTH, v.z);
    }

}
