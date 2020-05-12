package nl.tudelft.context.cg2.client.model;

import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;

/**
 * The model class.
 * Holds all the client data and data structures.
 */
public class Model {

    private final World world;

    /**
     * The model constructor.
     */
    public Model() {
        this.world = new World();
    }

    /**
     * Loads the client data from external resources.
     */
    public void load() {
        ImageCache.loadImages();
        world.create();
    }

    /**
     * World getter.
     * @return the game world.
     */
    public World getWorld() {
        return world;
    }
}
