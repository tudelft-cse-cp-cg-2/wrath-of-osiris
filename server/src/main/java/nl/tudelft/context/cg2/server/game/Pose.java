package nl.tudelft.context.cg2.server.game;

import javax.script.ScriptEngine;
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
            case DOWN:
                setLegs(Legs.RIGHTUP);
                return true;
            case RIGHTUP:
                return true;
            case LEFTUP:
            default:
                return false;
        }
    }

    /**
     * Setter for individually lowering the right leg.
     */
    public void setRightLegDown() {
        if (this.legs == Legs.RIGHTUP) {
            setLegs(Legs.DOWN);
        }
    }

    /**
     * Setter for individually raising the left leg.
     * @return false if invalid (legs can't both be raised), else true
     */
    public boolean setLeftLegUp() {
        switch (this.legs) {
            case DOWN:
                setLegs(Legs.LEFTUP);
                return true;
            case LEFTUP:
                return true;
            case RIGHTUP:
            default:
                return false;
        }
    }

    /**
     * Setter for individually lowering the left leg.
     */
    public void setLeftLegDown() {
        if (this.legs == Legs.LEFTUP) {
            setLegs(Legs.DOWN);
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


    /**
     * Unpack pose string packet coming from client into server format.
     * @param poseStr string packet coming from client
     * @return server Pose format object
     */
    public static Pose unpack(String poseStr) {
        ScreenPos screenPos = ScreenPos.valueOf(Character.getNumericValue(poseStr.charAt(0)));
        Arm leftArm = Arm.valueOf(Character.getNumericValue(poseStr.charAt(1)));
        Arm rightArm = Arm.valueOf(Character.getNumericValue(poseStr.charAt(2)));
        Legs legs;
        switch (poseStr.substring(3, 5)) {
            case "00": legs = Legs.DOWN; break;
            case "01": legs = Legs.RIGHTUP; break;
            case "10": legs = Legs.LEFTUP; break;
            default: legs = Legs.DOWN;
        }

        return new Pose(leftArm, rightArm, legs, screenPos);
    }



    /**
     * Pack to send over the Internet. Position format is:
     * [section][leftarm][rightarm][leftleg][rightleg]
     * Section being:
     * 0 - left
     * 1 - middle
     * 2 - right
     * Arms being:
     * 0 - bottom
     * 1 - middle
     * 2 - top
     * Legs being:
     * 0 - neutral
     * 1 - raised
     * @return a packed string representing this pose
     */
    public String pack() {
        String msg = "";
        msg += screenPos.indexOf();
        msg += leftArm.indexOf();
        msg += rightArm.indexOf();
        switch (legs) {
            case DOWN: msg += "00"; break;
            case LEFTUP: msg += "10"; break;
            case RIGHTUP: msg += "01"; break;
            default: msg += "00";
        }
        return msg;
    }
}
