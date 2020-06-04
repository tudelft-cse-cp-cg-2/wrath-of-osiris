package nl.tudelft.context.cg2.client.model.world.factories;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.entities.Hole;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;

import java.util.ArrayList;

/**
 * The wall factory class.
 * Generates a wall entity.
 */
@SuppressWarnings(value = "HideUtilityClassConstructor")
public class EntityFactory {

    public static final Vector3D HOLE_SIZE = new Vector3D(300D, 500D, 0D);

    /**
     * Generates a pseudo-random wall entity.
     * @return a new wall.
     */
    public static Wall generateWall() {
        Image image = ImageCache.IMAGES[0];
        Vector3D position = new Vector3D(0D, 0, World.DEPTH);
        Vector3D velocity = new Vector3D(0D, 0D, -70D);
        Vector3D size = new Vector3D(World.WIDTH, World.HEIGHT, 0);
        return new Wall(image, position, velocity, size);
    }

    /**
     * Generates 3 holes with random posture shapes.
     * @param wall the wall to generate them for.
     * @return a list of 3 holes.
     */
    public static ArrayList<Hole> generateHoles(Wall wall) {
        ArrayList<Hole> holes = new ArrayList<>();
        holes.add(new Hole(new Vector3D(World.WIDTH / 4 - HOLE_SIZE.x / 2,
                0, wall.getDepth() - 1), new Vector3D(wall.getVelocity()), new Pose(), 1));
        holes.add(new Hole(new Vector3D((World.WIDTH / 2 - HOLE_SIZE.x / 2),
                0, wall.getDepth() - 1), new Vector3D(wall.getVelocity()), new Pose(), 1));
        holes.add(new Hole(new Vector3D((World.WIDTH / 4 * 3 - HOLE_SIZE.x / 2),
                0, wall.getDepth() - 1), new Vector3D(wall.getVelocity()), new Pose(), 1));
        return holes;
    }
}
