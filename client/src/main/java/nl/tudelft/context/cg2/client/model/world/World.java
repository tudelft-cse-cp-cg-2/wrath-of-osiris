package nl.tudelft.context.cg2.client.model.world;

import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.entities.Avatar;
import nl.tudelft.context.cg2.client.model.world.entities.Hole;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;
import nl.tudelft.context.cg2.client.model.world.factories.WallFactory;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The World class.
 * Features the game world.
 */
public class World {

    public static final double WIDTH = 2000D;
    public static final double HEIGHT = 1200D;
    public static final double DEPTH = 500D;

    public static final Vector3D HOLE_SIZE = new Vector3D(300D, 500D, 0D);

    private final ArrayList<Entity> entities;
    private final ArrayList<Hole> holes;
    private Wall currentWall;
    private boolean inMotion;

    /**
     * The World Constructor.
     */
    public World() {
        this.inMotion = false;
        this.currentWall = null;
        this.entities = new ArrayList<>();
        this.holes = new ArrayList<>();
    }

    /**
     * Creates a new world.
     */
    public void create() {
        inMotion = false;
        entities.clear();

        currentWall = WallFactory.generateWall();
        entities.add(currentWall);

        holes.clear();
        holes.addAll(WallFactory.generateHoles(currentWall));
        entities.addAll(holes);

        Avatar avatarA = new Avatar(Color.DARKGREEN);
        entities.add(avatarA);
        Avatar avatarB = new Avatar(Color.DEEPPINK);
        avatarB.setPosition(new Vector3D(500, 0, 0));
        entities.add(avatarB);
        Avatar avatarC = new Avatar(Color.DARKMAGENTA);
        avatarC.setPosition(new Vector3D(1200, 0, 0));
        entities.add(avatarC);
        entities.sort(Comparator.comparing(Entity::getDepth).reversed());
    }

    /**
     * Clears the world.
     */
    public void destroy() {
        inMotion = false;
        entities.clear();
        holes.clear();
        currentWall = null;
    }

    /**
     * Processes one step in the game world.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last update.
     */
    public void step(double t, double dt) {
        if (inMotion) {
            entities.forEach(e -> e.step(t, dt));

            // Makes walls appear in sequence.
            if (currentWall != null && currentWall.hasDecayed()) {
                entities.remove(currentWall);
                currentWall = WallFactory.generateWall();
                entities.add(currentWall);
                entities.removeAll(holes);
                holes.clear();
                holes.addAll(WallFactory.generateHoles(currentWall));
                entities.addAll(holes);
                entities.sort(Comparator.comparing(Entity::getDepth).reversed());
            }
        }
    }

    /**
     * Gets the world entities.
     * @return the entities that are part of this world.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Returns the world Dimensions as a 3D vector.
     * @return the world dimension vector.
     */
    public Vector3D getDimensions() {
        return new Vector3D(WIDTH, HEIGHT, DEPTH);
    }

    /**
     * Checks if the world is in motion.
     * @return inMotion boolean.
     */
    public boolean isInMotion() {
        return inMotion;
    }

    /**
     * Sets the world in motion.
     * @param inMotion toggle.
     */
    public void setInMotion(boolean inMotion) {
        this.inMotion = inMotion;
    }
}
