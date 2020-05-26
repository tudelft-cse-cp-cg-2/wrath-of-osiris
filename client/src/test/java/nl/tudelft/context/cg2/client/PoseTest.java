package nl.tudelft.context.cg2.client;

import nl.tudelft.context.cg2.client.controller.logic.posedetection.Limb;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoseTest {
    @Test
    public void testPoseInit() {
        // options for arms
        Position leftArm = Position.top;
        Position rightArm = Position.bottom;
        // options for legs
        Position leftLeg = Position.raised;
        Position rightLeg = Position.neutral;
        Pose pose = new Pose(leftArm, rightArm, leftLeg, rightLeg);
        // Test pose
        assertEquals(pose.toString(), "Pose: la: " + leftArm.name() + "| ra: " + rightArm.name()
                + "| ll: " + leftLeg.name() + "| rl: " + rightLeg.name());
    }

    @Test
    public void testUpdatePose() {
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
        pose.incrementCounter(Limb.left_leg, Position.raised);
        pose.incrementCounter(Limb.left_leg, Position.neutral);
        // limb 2 option 0 and 1 have most votes
        pose.incrementCounter(Limb.right_leg, Position.raised);
        pose.incrementCounter(Limb.right_leg, Position.raised);
        // limb 3 option 0 has most votes
        pose.updatePose();
        // Pose has not updated because it is still below threshold
        assertEquals("Pose: la: " + Position.top.name() + "| ra: " + Position.top.name()
                        + "| ll: " + Position.neutral.name() + "| rl: " + Position.neutral.name(),
                pose.toString());

        for (int i = 0; i < 200; i++) {
            pose.incrementCounter(Limb.right_arm, Position.bottom);
        }
        pose.updatePose();
        assertEquals("Pose: la: " + Position.top.name() + "| ra: " + Position.bottom.name()
                        + "| ll: " + Position.neutral.name() + "| rl: " + Position.neutral.name(),
                pose.toString());
    }
}
