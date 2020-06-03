package nl.tudelft.context.cg2.client.model.world;

import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.entities.Avatar;
import nl.tudelft.context.cg2.client.model.world.entities.Hole;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;
import nl.tudelft.context.cg2.client.model.world.factories.EntityFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The World class.
 * Features the game world.
 */
public class World {

    public static final double WIDTH = 2000D;
    public static final double HEIGHT = 1200D;
    public static final double DEPTH = 500D;

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

        currentWall = EntityFactory.generateWall();
        entities.add(currentWall);

        holes.clear();
        holes.addAll(EntityFactory.generateHoles(currentWall));
        entities.addAll(holes);
        entities.sort(Comparator.comparing(Entity::getDepth).reversed());
    }

    /**
     * Creates the player avatars in the game world for all players in the game.
     * @param players the list of players that are in the game.
     */
    public void createPlayerAvatars(List<Player> players) {
        Color[] colors = {Color.DARKBLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.LIGHTBLUE};
        players.forEach(p -> {
            int random = ThreadLocalRandom.current().nextInt(0, 4);
            Avatar avatar = new Avatar(p, colors[random]);
            avatar.setPosition(new Vector3D((World.WIDTH - avatar.getSize().x) * 0.5D, 0D, 0D));
            entities.add(avatar);
        });
    }

    /**
     * Destroys the player avatar and updates
     * other world values when a player leaves the game.
     * @param player the player to whom the avatar belongs.
     */
    public void onAvatarDeath(Player player) {

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
                currentWall = EntityFactory.generateWall();
                entities.add(currentWall);
                entities.removeAll(holes);
                holes.clear();
                holes.addAll(EntityFactory.generateHoles(currentWall));
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
