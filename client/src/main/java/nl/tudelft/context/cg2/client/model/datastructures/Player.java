package nl.tudelft.context.cg2.client.model.datastructures;

import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Position;
import nl.tudelft.context.cg2.client.controller.io.posedetection.ScreenPos;

/**
 * Contains local information about a player.
 */
public class Player {
    private final String name;
    private Pose pose;
    private boolean poseChanged;

    /**
     * Constructor for the Player.
     * @param name the player's name.
     */
    public Player(String name) {
        this.name = name;
        this.pose = new Pose(ScreenPos.middle, Position.bottom, Position.bottom,
                Position.neutral, Position.neutral);
        this.poseChanged = false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return name.equals(player.getName());
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
     * Also updates the boolean 'poseChanged' whether the pose actually changed.
     * @param other the other pose.
     */
    public void updatePose(Pose other) {
        if (!pose.equals(other)) {
            this.pose = other;
            this.poseChanged = true;
            System.out.println(other.toString());
        }
    }

    /**
     * A boolean to check if the players pose has changed.
     * @return true if the pose has changed.
     */
    public boolean isPoseChanged() {
        return poseChanged;
    }

    /**
     * Sets the pose changed variable.
     * @param changed whether the pose has changed or not.
     */
    public void setPoseChanged(boolean changed) {
        poseChanged = changed;
    }
}
