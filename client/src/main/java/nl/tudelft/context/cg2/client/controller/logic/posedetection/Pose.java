package nl.tudelft.context.cg2.client.controller.logic.posedetection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class representing the pose of a human body.
 */
public class Pose {
    private Position leftArm;
    private Position rightArm;
    private Position leftLeg;
    private Position rightLeg;

    private Map<Position, Integer> leftArmCounter;
    private Map<Position, Integer> rightArmCounter;
    private Map<Position, Integer> leftLegCounter;
    private Map<Position, Integer> rightLegCounter;

    /**
     * Constructor for pose.
     * @param la - left arm
     * @param ra - right arm
     * @param ll - left leg
     * @param rl - right leg
     */
    public Pose(Position la, Position ra, Position ll, Position rl) {
        leftArm = la;
        rightArm = ra;
        leftLeg = ll;
        rightLeg = rl;
    }

    /**
     * Get a string representation of the pose.
     * @return a string representation of the pose
     */
    @Override
    public String toString() {
        return "Pose: la: " + leftArm.name() + "| ra: " + rightArm.name() + "| ll: "
                + leftLeg.name() + "| rl: " + rightLeg.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pose pose = (Pose) o;
        return leftArm == pose.leftArm
                && rightArm == pose.rightArm
                && leftLeg == pose.leftLeg
                && rightLeg == pose.rightLeg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftArm, rightLeg, leftLeg, rightLeg);
    }

    /**
     * Reset the counters that determine the chance limbs are in
     * specific positions.
     */
    public void resetCounters() {
        leftArmCounter = new HashMap<>();
        leftArmCounter.put(Position.top, 0);
        leftArmCounter.put(Position.middle, 0);
        leftArmCounter.put(Position.bottom, 0);
        rightArmCounter = new HashMap<>();
        rightArmCounter.put(Position.top, 0);
        rightArmCounter.put(Position.middle, 0);
        rightArmCounter.put(Position.bottom, 0);
        leftLegCounter = new HashMap<>();
        leftLegCounter.put(Position.raised, 0);
        leftLegCounter.put(Position.neutral, 0);
        rightLegCounter = new HashMap<>();
        rightLegCounter.put(Position.raised, 0);
        rightLegCounter.put(Position.neutral, 0);
    }

    /**
     * Increments the counter for a limb, increasing the chance that the limb
     * is found to be in this pose.
     * @param limb - the limb in question
     * @param position - the pose it might be in
     */
    public void incrementCounter(Limb limb, Position position) {
        if (limb == Limb.left_arm) {
            leftArmCounter.put(position, leftArmCounter.get(position) + 1);
        } else if (limb == Limb.right_arm) {
            rightArmCounter.put(position, rightArmCounter.get(position) + 1);
        } else if (limb == Limb.left_leg) {
            leftLegCounter.put(position, leftLegCounter.get(position) + 1);
        } else if (limb == Limb.right_leg) {
            rightLegCounter.put(position, rightLegCounter.get(position) + 1);
        }
    }

    /**
     * Calculate the final pose, based on the weights that were set by
     * incrementCounter calls.
     */
    public void updatePose() {
        leftArm = getBestArmPosition(leftArmCounter);
        rightArm = getBestArmPosition(rightArmCounter);
        if (leftLegCounter.get(Position.neutral) > leftLegCounter.get(Position.raised)) {
            leftLeg = Position.neutral;
        } else {
            leftLeg = Position.raised;
        }
        if (rightLegCounter.get(Position.neutral) > rightLegCounter.get(Position.raised)) {
            rightLeg = Position.neutral;
        } else {
            rightLeg = Position.raised;
        }
    }

    /**
     * Get the best pose position for an arm.
     * @param map - the counter for the arm
     * @return the pose with the highest chance of being correct
     */
    private Position getBestArmPosition(Map<Position, Integer> map) {
        if (map.get(Position.top) > map.get(Position.middle)) {
            if (map.get(Position.top) > map.get(Position.bottom)) {
                return Position.top;
            } else {
                return Position.bottom;
            }
        } else if (map.get(Position.middle) > map.get(Position.bottom)) {
            return Position.middle;
        } else {
            return Position.bottom;
        }
    }

    /**
     * Pack to send over the Internet. Position format is:
     * [section][leftarm][rightarm][leftleg][rightleg]
     * Section being:
     * 0 - left
     * 1 - middle
     * 2 - right
     * Arms being:
     * 0 - top
     * 1 - middle
     * 2 - bottom
     * Legs being:
     * 0 - neutral
     * 1 - raised
     * @return a packed string representing this pose
     */
    public String pack() {
        String msg = "1";
        msg += packArm(leftArm);
        msg += packArm(rightArm);
        msg += packLeg(leftLeg);
        msg += packLeg(rightLeg);
        return msg;
    }

    /**
     * Packs arm Position object into a String to send over Internet.
     * @param arm the to-be-packed arm Position object
     * @return the String packet representing the arm Position
     */
    private String packArm(Position arm) {
        switch (arm) {
            case bottom: return "2";
            case middle: return "1";
            case top: return "0";
            default: throw new IllegalArgumentException("Illegal arm position: " + arm.toString());
        }
    }

    /**
     * Packs leg Position object into a String to send over Internet.
     * @param leg the to-be-packed leg Position object
     * @return the String packet representing the leg Position
     */
    private String packLeg(Position leg) {
        switch (leg) {
            case neutral: return "0";
            case raised: return "1";
            default: throw new IllegalArgumentException("Illegal leg position: " + leg.toString());
        }
    }

    /**
     * Unpack the String packet from server representing a player's pose.
     * @param poseStr String packet from server containing a player's pose
     * @return player pose interpreted from packet
     */
    public static Pose unpack(String poseStr) {
        Position leftArm = unpackArm(poseStr.charAt(1));
        Position rightArm = unpackArm(poseStr.charAt(2));
        Position leftLeg = unpackLeg(poseStr.charAt(3));
        Position rightLeg = unpackLeg(poseStr.charAt(4));
        return new Pose(leftArm, rightArm, leftLeg, rightLeg);
    }

    /**
     * Helper function for unpack to specifically unpack the arm position.
     * @param c character in the String packet representing the arm position
     * @return arm position interpreted from packet character
     */
    private static Position unpackArm(char c) {
        switch (c) {
            case '0': return Position.top;
            case '1': return Position.middle;
            case '2': return Position.bottom;
            default: throw new IllegalArgumentException("Illegal arm format: " + c);
        }
    }

    /**
     * Helper function for unpack to specifically unpack the leg position.
     * @param c character in the String packet representing the leg position
     * @return leg position interpreted from packet character
     */
    private static Position unpackLeg(char c) {
        switch (c) {
            case '0': return Position.neutral;
            case '1': return Position.raised;
            default: throw new IllegalArgumentException("Illegal leg format: " + c);
        }
    }

    /**
     * Getter for left arm position.
     * @return left arm position
     */
    public Position getLeftArm() {
        return leftArm;
    }

    /**
     * Getter for right arm position.
     * @return right arm position
     */
    public Position getRightArm() {
        return rightArm;
    }

    /**
     * Getter for left leg position.
     * @return left leg position
     */
    public Position getLeftLeg() {
        return leftLeg;
    }

    /**
     * Getter for right leg position.
     * @return right leg position
     */
    public Position getRightLeg() {
        return rightLeg;
    }
}
