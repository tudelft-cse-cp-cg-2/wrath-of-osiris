package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {
    @Test
    public void testEmptyWall() {
        Wall w = new Wall();
        for (ScreenPos position : ScreenPos.values()) {
            assertNull(w.getPose(position));
            assertNull(w.getNumber(position));
        }
    }

    @Test
    public void testSetNumber() {
        Wall w = new Wall();
        w.setNumber(ScreenPos.LEFT, 3);
        assertEquals(w.getNumber(ScreenPos.LEFT), 3);
    }

    @Test
    public void testSetPose() {
        Wall w = new Wall();
        Pose p = new Pose(Arm.DOWN, Arm.SIDE, Legs.RIGHTUP, ScreenPos.LEFT);
        w.setPose(ScreenPos.LEFT, p);
        assertEquals(w.getPose(ScreenPos.LEFT), p);
    }

    @Test
    public void testSetPoseMismatchedScreenpos() {
        Wall w = new Wall();
        Pose p = new Pose(Arm.DOWN, Arm.SIDE, Legs.RIGHTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, p);
        assertEquals(w.getPose(ScreenPos.LEFT), p);
    }

    @Test
    public void testCompareSucceedNoNumbers() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.MIDDLE, b);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(a);
        playerPoses.add(b);
        assertTrue(w.compare(playerPoses));
    }

    @Test
    public void testCompareFailNoNumbersMiddle() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.MIDDLE, b);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(a);
        Pose c = new Pose(Arm.UP, Arm.DOWN, Legs.LEFTUP, ScreenPos.MIDDLE);
        playerPoses.add(c);
        assertFalse(w.compare(playerPoses));
    }

    @Test
    public void testCompareFailNoNumbersLeft() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.RIGHT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.LEFT);
        w.setPose(ScreenPos.RIGHT, a);
        w.setPose(ScreenPos.LEFT, b);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(a);
        Pose c = new Pose(Arm.UP, Arm.DOWN, Legs.LEFTUP, ScreenPos.LEFT);
        playerPoses.add(c);
        assertFalse(w.compare(playerPoses));
    }

    @Test
    public void testCompareFailNoNumbersRight() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.MIDDLE);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.RIGHT);
        w.setPose(ScreenPos.MIDDLE, a);
        w.setPose(ScreenPos.RIGHT, b);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(a);
        Pose c = new Pose(Arm.UP, Arm.DOWN, Legs.LEFTUP, ScreenPos.RIGHT);
        playerPoses.add(c);
        assertFalse(w.compare(playerPoses));
    }

    @Test
    public void testCompareFailTooFewForNumber() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.MIDDLE, b);
        w.setNumber(ScreenPos.MIDDLE, 2);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(a);
        playerPoses.add(b);
        assertFalse(w.compare(playerPoses));
    }

    @Test
    public void testCompareFailTooManyForNumber() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.RIGHT);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.RIGHT, b);
        w.setNumber(ScreenPos.RIGHT, 1);
        ArrayList<Pose> playerPoses = new ArrayList<>(2);
        playerPoses.add(b);
        playerPoses.add(b);
        assertFalse(w.compare(playerPoses));
    }

    @Test
    public void testToString() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.MIDDLE, b);
        w.setNumber(ScreenPos.LEFT, 2);
        String expected = "2\narms: DOWN UP\nlegs: DOWN\nscreen: LEFT\nnull\narms: UP SIDE" +
                "\nlegs: LEFTUP\nscreen: MIDDLE\nnull\nnull\n";
        assertEquals(expected, w.toString());
    }
}
