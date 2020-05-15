package nl.tudelft.context.cg2.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoseRegionTest {
    @Test
    public void poseRegionInit() {
        int tx = 0;
        int ty = 0;
        int bx = 250;
        int by = 250;
        int limb = 1;
        int option = 0;
        PoseRegion poseRegion = new PoseRegion(tx, ty, bx, by, limb, option);

        // Test getters
        assertTrue(poseRegion.getLeftX() == tx);
        assertTrue(poseRegion.getTopY() == ty);
        assertTrue(poseRegion.getRightX() == bx);
        assertTrue(poseRegion.getBottomY() == by);
        assertTrue(poseRegion.getLimb() == limb);
        assertTrue(poseRegion.getOption() == option);

        // Test Point getters
        assertTrue(poseRegion.getTopLeft() != null);
        assertTrue(Math.round(poseRegion.getTopLeft().x) == tx);
        assertTrue(Math.round(poseRegion.getBottomRight().y) == by);
    }

    @Test
    public void pointInRangeTrue() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, 0, 0);
        assertTrue(poseRegion.inRange(50, 50));
    }

    @Test
    public void pointInRangeFalse() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, 0, 0);
        assertTrue(!poseRegion.inRange(350, 350));
    }

    @Test
    public void pointInRangeBoundary() {
        PoseRegion poseRegion = new PoseRegion(0, 0, 250, 250, 0, 0);
        assertTrue(poseRegion.inRange(50, 0));
    }
}
