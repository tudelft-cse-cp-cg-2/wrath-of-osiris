package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {

    @Test
    public void testWallConstructor() {
        Vector3D position = new Vector3D(1,2,3);
        Vector3D velocity = new Vector3D(4,4,4);
        Vector3D size = new Vector3D(9,7,5);
        Wall wall = new Wall(null, position, velocity, size);

        assertEquals(wall.getVelocity(), velocity);
        assertEquals(wall.getPosition(), position);
        assertEquals(wall.getSize(), size);
        assertNull(wall.getTexture());
    }

    @Test
    public void testWallStep() {
        Vector3D position = new Vector3D(1,2,3);
        Vector3D velocity = new Vector3D(4,4,4);
        Wall wall = new Wall(null, position, velocity, null);
        wall.step(0, 1);

        assertEquals(wall.getPosition(), new Vector3D(5,6,7));
    }

}
