package nl.tudelft.context.cg2.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the pose of a human body.
 */
public class Pose {
    private int leftArm;
    private int rightArm;
    private int leftLeg;
    private int rightLeg;

    private Map<Integer, Integer> leftArmCounter;
    private Map<Integer, Integer> rightArmCounter;
    private Map<Integer, Integer> leftLegCounter;
    private Map<Integer, Integer> rightLegCounter;

    /**
     * Constructor for pose.
     * @param la - left arm
     * @param ra - right arm
     * @param ll - left leg
     * @param rl - right leg
     */
    public Pose(int la, int ra, int ll, int rl) {
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
        return "Pose: la: " + leftArm + "| ra: " + rightArm + "| ll: " + leftLeg + "| rl: "
                + rightLeg;
    }

    /**
     * Reset the counters that determine the chance limbs are in.
     * specific positions
     */
    public void resetCounters() {
        leftArmCounter = new HashMap<>();
        leftArmCounter.put(0, 0);
        leftArmCounter.put(1, 0);
        leftArmCounter.put(2, 0);
        rightArmCounter = new HashMap<>();
        rightArmCounter.put(0, 0);
        rightArmCounter.put(1, 0);
        rightArmCounter.put(2, 0);
        leftLegCounter = new HashMap<>();
        leftLegCounter.put(0, 0);
        leftLegCounter.put(1, 0);
        rightLegCounter = new HashMap<>();
        rightLegCounter.put(0, 0);
        rightLegCounter.put(1, 0);
    }

    /**
     * Increments the counter for a limb, increasing the chance that the limb
     * is found to be in this pose.
     * @param limb - the limb in question
     * @param option - the pose it might be in
     */
    public void incrementCounter(int limb, int option) {
        if (limb == 0) {
            leftArmCounter.put(option, leftArmCounter.get(option) + 1);
            return;
        }
        if (limb == 1) {
            rightArmCounter.put(option, rightArmCounter.get(option) + 1);
            return;
        }
        if (limb == 2) {
            leftLegCounter.put(option, leftLegCounter.get(option) + 1);
            return;
        }
        rightLegCounter.put(option, rightLegCounter.get(option) + 1);
    }

    /**
     * Calculate the final pose, based on the weights that were set by
     * incrementCounter calls.
     */
    public void updatePose() {
        leftArm = getBestArmOption(leftArmCounter);
        rightArm = getBestArmOption(rightArmCounter);
        if (leftLegCounter.get(0) > leftLegCounter.get(1)) {
            leftLeg = 0;
        } else {
            leftLeg = 1;
        }
        if (rightLegCounter.get(0) > rightLegCounter.get(1)) {
            rightLeg = 0;
        } else {
            rightLeg = 1;
        }
    }

    /**
     * Get the best pose option for an arm.
     * @param map - the counter for the arm
     * @return the pose with the highest chance of being correct
     */
    private int getBestArmOption(Map<Integer, Integer> map) {
        if (map.get(0) > map.get(1)) {
            if (map.get(0) > map.get(2)) {
                return 0;
            } else {
                return 2;
            }
        } else if (map.get(1) > map.get(2)) {
            return 1;
        } else {
            return 2;
        }
    }
}
