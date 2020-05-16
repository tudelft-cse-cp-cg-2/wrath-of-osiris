package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PoseTest {
    @BeforeEach
    public void testSetUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.DOWN, ScreenPos.MIDDLE);
        Pose c = new Pose(Arm.UP, Arm.SIDE, Legs.RIGHTUP, ScreenPos.MIDDLE);
    }

    @Test
    public void testEqualsAfterSetting() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.DOWN, ScreenPos.MIDDLE);
        b.setLeftArm(Arm.DOWN);
        b.setRightArm(Arm.UP);
        b.setScreenPos(ScreenPos.LEFT);
        assertEquals(a, b);
    }

    @Test
    public void testEqualsSelf() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertEquals(a, a);
    }

    @Test
    public void testEqualsOtherObject() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        String s = "makes no sense";
        assertNotEquals(a, s);
    }

    @Test
    public void testSetRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        a.setRightLegUp();
        assertEquals(a, b);
    }

    @Test
    public void testSetLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        a.setLeftLegUp();
        assertEquals(a, b);
    }

    @Test
    public void testSetLeftLegDown() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        a.setLeftLegDown();
        assertEquals(a, b);
    }

    @Test
    public void testSetRightLegDown() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        a.setRightLegDown();
        assertEquals(a, b);
    }

    @Test
    public void testInvalidSetRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertFalse(a.setRightLegUp());
    }

    @Test
    public void testInvalidSetLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertFalse(a.setLeftLegUp());
    }

    @Test
    public void testNoChangeSetLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertTrue(a.setLeftLegUp());
        assertEquals(a, b);
    }

    @Test
    public void testNoChangeSetRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertTrue(a.setRightLegUp());
        assertEquals(a, b);
    }

    @Test
    public void testToString() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertEquals("arms: DOWN UP\nlegs: RIGHTUP\nscreen: LEFT", a.toString());
    }

}

