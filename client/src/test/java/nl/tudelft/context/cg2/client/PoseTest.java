package nl.tudelft.context.cg2.client;

import nl.tudelft.context.cg2.client.posedetection.Limb;
import nl.tudelft.context.cg2.client.posedetection.Pose;
import nl.tudelft.context.cg2.client.posedetection.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoseTest {
    @Test
    public void poseInit() {
        // options for arms
        Position leftArm = Position.top;
        Position rightArm = Position.bottom;
        // options for legs
        Position leftLeg = Position.raised;
        Position rightLeg = Position.neutral;
        Pose pose = new Pose(leftArm, rightArm, leftLeg, rightLeg);
        // Test pose
        assertTrue(pose.toString()
                .equals("Pose: la: " + leftArm.name() + "| ra: " + rightArm.name() + "| ll: " + leftLeg.name() + "| rl: " + rightLeg.name()));
    }

    @Test
    public void updatePoseOnce() {
        Pose pose = new Pose(Position.top, Position.top, Position.neutral, Position.neutral);
        pose.resetCounters();
        pose.incrementCounter(Limb.right_arm, Position.top);
        pose.incrementCounter(Limb.right_arm, Position.middle);
        pose.incrementCounter(Limb.right_arm, Position.middle);
        // limb 0 option 1 has most votes
        pose.incrementCounter(Limb.left_arm, Position.top);
        pose.incrementCounter(Limb.left_arm, Position.middle);
        pose.incrementCounter(Limb.left_arm, Position.middle);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        // limb 1 option 2 has most votes
        pose.incrementCounter(Limb.left_leg, Position.top);
        pose.incrementCounter(Limb.left_leg, Position.middle);
        // limb 2 option 0 and 1 have most votes
        pose.incrementCounter(Limb.right_leg, Position.top);
        pose.incrementCounter(Limb.right_leg, Position.top);
        // limb 3 option 0 has most votes
        pose.updatePose();
        assertTrue(pose.toString()
                .equals("Pose: la: " + Position.middle.name() + "| ra: " + Position.bottom.name()
                        + "| ll: " + Position.middle.name() + "| rl: " + Position.top.name()));
    }

    @Test
    public void updatePoseTwice() {
        Pose pose = new Pose(Position.top, Position.top, Position.neutral, Position.neutral);
        pose.resetCounters();
        pose.incrementCounter(Limb.right_arm, Position.top);
        pose.incrementCounter(Limb.right_arm, Position.middle);
        pose.incrementCounter(Limb.right_arm, Position.middle);
        // limb 0 option 1 has most votes
        pose.incrementCounter(Limb.left_arm, Position.top);
        pose.incrementCounter(Limb.left_arm, Position.middle);
        pose.incrementCounter(Limb.left_arm, Position.middle);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        pose.incrementCounter(Limb.left_arm, Position.bottom);
        // limb 1 option 2 has most votes
        pose.incrementCounter(Limb.left_leg, Position.top);
        pose.incrementCounter(Limb.left_leg, Position.middle);
        // limb 2 option 0 and 1 have most votes
        pose.incrementCounter(Limb.right_leg, Position.top);
        pose.incrementCounter(Limb.right_leg, Position.top);
        // limb 3 option 0 has most votes
        pose.updatePose();
        assertTrue(pose.toString()
                .equals("Pose: la: " + Position.middle.name() + "| ra: " + Position.bottom.name()
                        + "| ll: " + Position.middle.name() + "| rl: " + Position.top.name()));

        pose.incrementCounter(Limb.right_arm, Position.top);
        pose.incrementCounter(Limb.right_arm, Position.top);
        pose.updatePose();
        assertTrue(pose.toString()
                .equals("Pose: la: " + Position.top.name() + "| ra: " + Position.bottom.name()
                        + "| ll: " + Position.middle.name() + "| rl: " + Position.top.name()));
    }
}
