package nl.tudelft.context.cg2.server.game;

import java.util.Objects;

/**
 * The Pose object has 2 purposes: it's the interpreted form of a player's pose, and it's used to
 * shape holes in a Wall.
 */
public class Pose {
    private Arm leftArm;
    private Arm rightArm;
    private Legs legs;
    private ScreenPos screenPos;

    /**
     * Constructor.
     * @param leftArm   left arm
     * @param rightArm  right arm
     * @param legs      legs
     * @param screenPos position on screen
     */
    public Pose(Arm leftArm, Arm rightArm, Legs legs, ScreenPos screenPos) {
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        this.legs = legs;
        this.screenPos = screenPos;
    }

    /**
     * Getter.
     * @return left arm
     */
    public Arm getLeftArm() {
        return leftArm;
    }

    /**
     * Setter.
     * @param leftArm left arm
     */
    public void setLeftArm(Arm leftArm) {
        this.leftArm = leftArm;
    }

    /**
     * Getter.
     * @return right arm
     */
    public Arm getRightArm() {
        return rightArm;
    }

    /**
     * Setter.
     * @param rightArm right arm
     */
    public void setRightArm(Arm rightArm) {
        this.rightArm = rightArm;
    }

    /**
     * Getter.
     * @return legs
     */
    public Legs getLegs() {
        return legs;
    }

    /**
     * Setter.
     * @param legs legs
     */
    public void setLegs(Legs legs) {
        this.legs = legs;
    }

    /**
     * Getter.
     * @return screen position
     */
    public ScreenPos getScreenPos() {
        return screenPos;
    }

    /**
     * Setter.
     * @param screenPos screen position
     */
    public void setScreenPos(ScreenPos screenPos) {
        this.screenPos = screenPos;
    }

    /**
     * Setter for individually raising the right leg.
     * @return false if invalid (legs can't both be raised), else true
     */
    public boolean setRightLegUp() {
        switch (this.legs) {
            case DOWNDOWN:
                setLegs(Legs.DOWNUP);
                return true;
            case DOWNUP:
                return true;
            case UPDOWN:
            default:
                return false;
        }
    }

    /**
     * Setter for individually lowering the right leg.
     */
    public void setRightLegDown() {
        if (this.legs == Legs.DOWNUP) {
            setLegs(Legs.DOWNDOWN);
        }
    }

    /**
     * Setter for individually raising the left leg.
     * @return false if invalid (legs can't both be raised), else true
     */
    public boolean setLeftLegUp() {
        switch (this.legs) {
            case DOWNDOWN:
                setLegs(Legs.UPDOWN);
                return true;
            case UPDOWN:
                return true;
            case DOWNUP:
            default:
                return false;
        }
    }

    /**
     * Setter for individually lowering the left leg.
     */
    public void setLeftLegDown() {
        if (this.legs == Legs.UPDOWN) {
            setLegs(Legs.DOWNDOWN);
        }
    }

    /**
     * Compares a Pose to another, and returns true if they're the same. This method is intended to
     * be used to compare players' poses to the holes in the walls.
     * @param o other Pose to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pose pose = (Pose) o;
        return getLeftArm() == pose.getLeftArm()
                && getRightArm() == pose.getRightArm()
                && getLegs() == pose.getLegs()
                && getScreenPos() == pose.getScreenPos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeftArm(), getRightArm(), getLegs(), getScreenPos());
    }

    /**
     * Returns a string representation of the Pose, for debugging purposes.
     * @return String representation
     */
    public String toString() {
        return "arms: " + leftArm + " " + rightArm
                + "\nlegs: " + legs
                + "\nscreen: " + screenPos;
    }
}
