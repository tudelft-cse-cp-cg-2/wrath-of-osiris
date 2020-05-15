package nl.tudelft.context.cg2.client.posedetection;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Reset the counters that determine the chance limbs are in.
     * specific positions
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
            System.out.println("update left");
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
}
