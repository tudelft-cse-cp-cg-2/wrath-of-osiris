package nl.tudelft.context.cg2.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoseTest {
    @Test
    public void poseInit() {
        // options for arms
        int leftArm = 0;
        int rightArm = 2;
        // options for legs
        int leftLeg = 1;
        int rightLeg = 0;
        Pose pose = new Pose(leftArm, rightArm, leftLeg, rightLeg);
        // Test pose
        assertTrue(pose.toString()
                .equals("Pose: la: " + leftArm + "| ra: " + rightArm + "| ll: " + leftLeg + "| rl: " + rightLeg));
    }

    @Test
    public void updatePoseOnce() {
        Pose pose = new Pose(0, 0, 0, 0);
        pose.resetCounters();
        pose.incrementCounter(0, 0);
        pose.incrementCounter(0, 1);
        pose.incrementCounter(0, 1);
        // limb 0 option 1 has most votes
        pose.incrementCounter(1, 0);
        pose.incrementCounter(1, 1);
        pose.incrementCounter(1, 1);
        pose.incrementCounter(1, 2);
        pose.incrementCounter(1, 2);
        pose.incrementCounter(1, 2);
        // limb 1 option 2 has most votes
        pose.incrementCounter(2, 0);
        pose.incrementCounter(2, 1);
        // limb 2 option 0 and 1 have most votes
        pose.incrementCounter(3, 0);
        pose.incrementCounter(3, 0);
        // limb 3 option 0 has most votes
        pose.updatePose();
        assertTrue(pose.leftArm == 1);
        assertTrue(pose.rightArm == 2);
        assertTrue(pose.leftLeg == 1);
        assertTrue(pose.rightLeg == 0);
    }

    @Test
    public void updatePoseTwice() {
        Pose pose = new Pose(0, 0, 0, 0);
        pose.resetCounters();
        pose.incrementCounter(0, 0);
        pose.incrementCounter(0, 1);
        pose.incrementCounter(0, 1);
        // limb 0 option 1 has most votes
        pose.incrementCounter(1, 0);
        pose.incrementCounter(1, 1);
        pose.incrementCounter(1, 1);
        pose.incrementCounter(1, 2);
        pose.incrementCounter(1, 2);
        pose.incrementCounter(1, 2);
        // limb 1 option 2 has most votes
        pose.incrementCounter(2, 0);
        pose.incrementCounter(2, 1);
        // limb 2 option 0 and 1 have most votes
        pose.incrementCounter(3, 0);
        pose.incrementCounter(3, 0);
        // limb 3 option 0 has most votes
        pose.updatePose();
        assertTrue(pose.toString()
                .equals("Pose: la: " + 1 + "| ra: " + 2 + "| ll: " + 1 + "| rl: " + 0));

        pose.incrementCounter(0, 0);
        pose.incrementCounter(0, 0);
        pose.updatePose();
        assertTrue(pose.toString()
                .equals("Pose: la: " + 0 + "| ra: " + 2 + "| ll: " + 1 + "| rl: " + 0));
    }
}
