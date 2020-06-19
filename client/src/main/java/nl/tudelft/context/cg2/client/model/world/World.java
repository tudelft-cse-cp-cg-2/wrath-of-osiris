package nl.tudelft.context.cg2.client.model.world;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.model.datastructures.BackendPose;
import nl.tudelft.context.cg2.client.model.datastructures.BackendWall;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.entities.Avatar;
import nl.tudelft.context.cg2.client.model.world.entities.Hole;
import nl.tudelft.context.cg2.client.model.world.entities.Wall;
import nl.tudelft.context.cg2.client.model.world.factories.EntityFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

    private ArrayList<BackendWall> level;
    private int wallIdx;
    private int levelIdx;
    private int lives;

    private final ArrayList<Entity> entities;
    private final ArrayList<Hole> holes;
    private Wall currentWall;
    private boolean inMotion;

    public final SimpleBooleanProperty waveCompleted;

    /**
     * The World Constructor.
     */
    public World() {
        this.waveCompleted = new SimpleBooleanProperty(false);
        this.entities = new ArrayList<>();
        this.holes = new ArrayList<>();
        this.inMotion = false;
        this.currentWall = null;
        this.level = null;
        this.wallIdx = 0;
        this.levelIdx = 0;
        this.lives = 0;
    }

    /**
     * Creates a new world.
     */
    public void create() {
        waveCompleted.set(false);
        inMotion = false;
        entities.clear();
        holes.clear();
        currentWall = EntityFactory.generateWall();
        entities.add(currentWall);
        levelIdx = 0;
    }

    /**
     * Clears the world.
     */
    public void destroy() {
        inMotion = false;
        waveCompleted.set(false);
        entities.clear();
        holes.clear();
        currentWall = null;
        level = null;
        wallIdx = 0;
        // levelIdx is reset during creation, to be able to display in summary.
        lives = 0;
    }

    /**
     * Completes a wall wave.
     */
    private void completeWave() {
        inMotion = false;
        waveCompleted.set(true);
    }

    /**
     * Starts a new wall wave in the world.
     */
    public void startWave() {
        if (wallIdx < 0 || wallIdx >= level.size()) {
            System.out.println("Next wall not found for the current level.");
            System.out.println("wallIdx: " + wallIdx);
            return;
        }

        //Creates a new wall.
        entities.remove(currentWall);
        currentWall = EntityFactory.generateWall();
        entities.add(currentWall);

        //Creates the hole for the current level wave.
        entities.removeAll(holes);
        holes.clear();
        BackendWall next = level.get(wallIdx);
        HashMap<BackendPose, Integer> poses = next.getPoses();
        poses.forEach((k, v) -> holes.add(EntityFactory.generateHole(currentWall, k, v)));
        entities.addAll(holes);
        entities.sort(Comparator.comparing(Entity::getDepth).reversed());

        //Starts the new wave.
        inMotion = true;
        waveCompleted.set(false);
        wallIdx++;
        System.out.println("Finished wave");
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
            if (currentWall != null && currentWall.hasDecayed() && !waveCompleted.get()) {
                completeWave();
            }
        }
    }

    /**
     * Creates the player avatars in the game world for all players in the game.
     * @param players the list of players that are in the game.
     */
    public void createPlayerAvatars(List<Player> players) {
        players.forEach(p -> {
            Color color = selectColor(players, p.getName());
            Avatar avatar = new Avatar(color, p.getName());
            avatar.setPosition(new Vector3D((World.WIDTH - avatar.getSize().x) * 0.5D, 0D, 0D));
            p.setAvatar(avatar);
            entities.add(avatar);
        });
    }

    /**
     * Selects a color for the player based on your player
     * name and all other players in the game.
     * @param players the players in the game.
     * @param name the name of your player.
     * @return a color.
     */
    private Color selectColor(List<Player> players, String name) {
        Color[] colors = {Color.DARKBLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.LIGHTBLUE};
        int random = ThreadLocalRandom.current().nextInt(0, 4);
        return colors[random];
    }

    /**
     * Destroys the player avatar and updates other world values when a player leaves the game.
     * @param avatar the avatar to be removed.
     * @return whether the operation was successful.
     */
    public boolean onAvatarDeath(Avatar avatar) {
        return entities.remove(avatar);
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

    /**
     * Gets the shared lives of all the players in the world.
     * @return the lives field.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the lives of all the players in the world.
     * @param lives sets the live field.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the level.
     * @return the level.
     */
    public ArrayList<BackendWall> getLevel() {
        return level;
    }

    /**
     * Sets the level featured in the world.
     * @param level the level.
     */
    public void setLevel(ArrayList<BackendWall> level) {
        this.level = level;
        wallIdx = 0;
    }

    /**
     * Getter for wall index.
     * @return the current wall index
     */
    public int getWallIdx() {
        return wallIdx;
    }

    /**
     * Getter for level index.
     * @return the current level index
     */
    public int getLevelIdx() {
        return levelIdx;
    }

    /**
     * Setter for level index.
     * @return the current level index
     */
    public void setLevelIdx(int levelIdx) {
        this.levelIdx = levelIdx;
    }
}
