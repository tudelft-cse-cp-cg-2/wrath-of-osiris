package nl.tudelft.context.cg2.client;

import nl.tudelft.context.cg2.client.controller.logic.posedetection.Limb;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.PoseRegion;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoseRegionTest {
    @Test
    public void testPoseRegionInit() {
        int tx = 0;
        int ty = 0;
        int bx = 250;
        int by = 250;
        Limb limb = Limb.left_arm;
        Position position = Position.middle;
        PoseRegion poseRegion = new PoseRegion(tx, ty, bx, by, limb, position);

        // Test getters
        assertTrue(poseRegion.getLeftX() == tx);
        assertTrue(poseRegion.getTopY() == ty);
        assertTrue(poseRegion.getRightX() == bx);
        assertTrue(poseRegion.getBottomY() == by);
        assertTrue(poseRegion.getLimb() == limb);
        assertTrue(poseRegion.getPosition() == position);

        // Test Point getters
        assertTrue(poseRegion.getTopLeft() != null);
        assertTrue(Math.round(poseRegion.getTopLeft().x) == tx);
        assertTrue(Math.round(poseRegion.getBottomRight().y) == by);
    }

    @Test
    public void testPointInRangeTrue() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, Limb.left_leg, Position.neutral);
        assertTrue(poseRegion.inRange(50, 50));
    }

    @Test
    public void testPointInRangeFalse() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, Limb.left_leg, Position.neutral);
        assertTrue(!poseRegion.inRange(350, 350));
    }

    @Test
    public void testPointInRangeBoundary() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, Limb.left_leg, Position.neutral);
        assertTrue(poseRegion.inRange(50, 0));
    }
}
