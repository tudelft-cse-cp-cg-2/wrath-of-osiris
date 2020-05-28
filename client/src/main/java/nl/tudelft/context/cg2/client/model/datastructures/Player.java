package nl.tudelft.context.cg2.client.model.datastructures;

import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Position;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.ScreenPos;
import nl.tudelft.context.cg2.client.model.world.entities.Avatar;

/**
 * Contains local information about a player.
 */
public class Player {
    private final String name;
    private Avatar avatar;

    /**
     * Current pose of the player.
     * This starts out with all limbs neutral, in the middle screen position.
     */
    private Pose pose =
            new Pose(ScreenPos.middle, Position.bottom, Position.bottom,
                    Position.neutral, Position.neutral);
    private boolean poseChanged = false;

    /**
     * Constructor for the Player.
     * @param name the player's name.
     */
    public Player(String name) {
        this.name = name;
        this.avatar = null;
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
     * @param otherPose the player's current pose.
     */
    public void setPose(Pose otherPose) {
        if (!pose.equals(otherPose)) {
            this.pose = pose;
            this.poseChanged = true;
        }

    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public boolean isPoseChanged() {
        return poseChanged;
    }

    public void setPoseChanged(boolean bool) {
        this.poseChanged = bool;
    }
}
