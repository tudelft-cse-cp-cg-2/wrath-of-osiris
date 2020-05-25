package nl.tudelft.context.cg2.client.model.datastructures;

import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Position;

/**
 * Contains local information about a player.
 */
public class Player {
    private final String name;
    private Pose pose;

    /**
     * Constructor for the Player.
     * @param name the player's name.
     */
    public Player(String name) {
        this.name = name;
        this.pose = new Pose(Position.bottom, Position.bottom, Position.neutral, Position.neutral);
    }

    /**
     * Getter for a player's name.
     * @return the player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for a player's pose.
     * @return the player's current pose.
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Setter for a player's pose.
     * @param pose the player's current pose.
     */
    public void setPose(Pose pose) {
        this.pose = pose;
    }
}
