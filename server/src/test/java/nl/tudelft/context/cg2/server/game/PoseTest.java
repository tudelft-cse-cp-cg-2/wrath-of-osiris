package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PoseTest {
    @BeforeEach
    void setUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.DOWN, ScreenPos.MIDDLE);
        Pose c = new Pose(Arm.UP, Arm.SIDE, Legs.RIGHTUP, ScreenPos.MIDDLE);
    }

    @Test
    void equalsAfterSetting() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.DOWN, ScreenPos.MIDDLE);
        b.setLeftArm(Arm.DOWN);
        b.setRightArm(Arm.UP);
        b.setScreenPos(ScreenPos.LEFT);
        assertEquals(a, b);
    }

    @Test
    void equalsSelf() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertEquals(a, a);
    }

    @Test
    void equalsOtherObject() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        String s = "makes no sense";
        assertNotEquals(a, s);
    }

    @Test
    void setRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        a.setRightLegUp();
        assertEquals(a, b);
    }

    @Test
    void setLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        a.setLeftLegUp();
        assertEquals(a, b);
    }

    @Test
    void setLeftLegDown() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        a.setLeftLegDown();
        assertEquals(a, b);
    }

    @Test
    void setRightLegDown() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        a.setRightLegDown();
        assertEquals(a, b);
    }

    @Test
    void invalidSetRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertFalse(a.setRightLegUp());
    }

    @Test
    void invalidSetLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertFalse(a.setLeftLegUp());
    }

    @Test
    void noChangeSetLeftLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.LEFTUP, ScreenPos.LEFT);
        assertTrue(a.setLeftLegUp());
        assertEquals(a, b);
    }

    @Test
    void noChangeSetRightLegUp() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        Pose b = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertTrue(a.setRightLegUp());
        assertEquals(a, b);
    }

    @Test
    void toStringTest() {
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.RIGHTUP, ScreenPos.LEFT);
        assertEquals("arms: DOWN UP\nlegs: DOWNUP\nscreen: LEFT", a.toString());
    }

}

