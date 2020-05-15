package nl.tudelft.context.cg2.client;

import java.util.HashMap;
import java.util.Map;

public class Pose {
    public int leftArm;
    public int rightArm;
    public int leftLeg;
    public int rightLeg;

    private Map<Integer, Integer> leftArmCounter;
    private Map<Integer, Integer> rightArmCounter;
    private Map<Integer, Integer> leftLegCounter;
    private Map<Integer, Integer> rightLegCounter;

    public Pose(int la, int ra, int ll, int rl) {
        leftArm = la;
        rightArm = ra;
        leftLeg = ll;
        rightLeg = rl;
    }

    @Override
    public String toString() {
        return "Pose: la: " + leftArm + "| ra: " + rightArm + "| ll: " + leftLeg + "| rl: " + rightLeg;
    }

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

    public void updatePose() {
        leftArm = _getBestOption(leftArmCounter);
        rightArm = _getBestOption(rightArmCounter);
        leftLeg = leftLegCounter.get(0) > leftLegCounter.get(1) ? 0 : 1;
        rightLeg = rightLegCounter.get(0) > rightLegCounter.get(1) ? 0 : 1;
    }

    private int _getBestOption(Map<Integer, Integer> map) {
        return map.get(0) > map.get(1)
                ? map.get(0) > map.get(2) ? 0 : 2
                : map.get(1) > map.get(2) ? 1 : 2;
    }
}
