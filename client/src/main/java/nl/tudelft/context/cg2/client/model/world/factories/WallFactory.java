package nl.tudelft.context.cg2.client.model.world.factories;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;

/**
 * The wall factory class.
 * Generates a wall entity.
 */
@SuppressWarnings(value = "HideUtilityClassConstructor")
public class WallFactory {

    /**
     * Generates a pseudo-random wall entity.
     * @return a new wall.
     */
    public static Wall generateWall() {
        Image image = ImageCache.IMAGES[0];
        Vector3D position = new Vector3D(0D, 0, World.DEPTH);
        Vector3D velocity = new Vector3D(0D, 0D, -60D);
        Vector3D size = new Vector3D(World.WIDTH, World.HEIGHT, 0);
        return new Wall(image, position, velocity, size);
    }
}
