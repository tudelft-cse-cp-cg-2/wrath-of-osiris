package nl.tudelft.context.cg2.client.model.world;

import nl.tudelft.context.cg2.client.model.world.factories.WallFactory;

import java.util.ArrayList;

public class World {

    public static final double WIDTH = 2000D;
    public static final double HEIGHT = 1200D;
    public static final double DEPTH = 500D;

    private final ArrayList<Entity> entities;

    public World() {
        this.entities = new ArrayList<>();
    }

    /**
     * Creates a new world.
     */
    public void create() {
        entities.clear();
        entities.add(WallFactory.generateWall());
    }

    /**
     * Processes one step in the game world.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last update.
     */
    public void step(double t, double dt) {
        entities.forEach(e -> {
            e.step(t, dt);
        });
    }

    /**
     * Gets the world entities.
     * @return the entities that are part of this world.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
