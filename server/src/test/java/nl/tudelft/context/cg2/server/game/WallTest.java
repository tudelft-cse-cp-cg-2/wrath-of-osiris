package nl.tudelft.context.cg2.server.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {
    @Test
    void empty_wall() {
        Wall w = new Wall();
        for (ScreenPos position : ScreenPos.values()) {
            assertNull(w.getPose(position));
            assertNull(w.getNumber(position));
        }
    }

    @Test
    void set_number() {
        Wall w = new Wall();
        w.setNumber(ScreenPos.LEFT, 3);
        assertEquals(w.getNumber(ScreenPos.LEFT), 3);
    }

    @Test
    void set_pose() {
        Wall w = new Wall();
        Pose p = new Pose(Arm.DOWN, Arm.SIDE, Legs.RIGHTUP, ScreenPos.LEFT);
        w.setPose(ScreenPos.LEFT, p);
        assertEquals(w.getPose(ScreenPos.LEFT), p);
    }

    @Test
    void set_pose_mismatched_screenpos() {
        Wall w = new Wall();
        Pose p = new Pose(Arm.DOWN, Arm.SIDE, Legs.RIGHTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, p);
        assertEquals(w.getPose(ScreenPos.LEFT), p);
    }

    @Test
    void compare_succeed_no_numbers() {
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
    void compare_fail_no_numbers_middle() {
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
    void compare_fail_no_numbers_left() {
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
    void compare_fail_no_numbers_right() {
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
    void compare_fail_too_few_for_number() {
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
    void compare_fail_too_many_for_number() {
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
    void to_string() {
        Wall w = new Wall();
        Pose a = new Pose(Arm.DOWN, Arm.UP, Legs.DOWN, ScreenPos.LEFT);
        Pose b = new Pose(Arm.UP, Arm.SIDE, Legs.LEFTUP, ScreenPos.MIDDLE);
        w.setPose(ScreenPos.LEFT, a);
        w.setPose(ScreenPos.MIDDLE, b);
        w.setNumber(ScreenPos.LEFT, 2);
        String expected = "2\narms: DOWN UP\nlegs: DOWNDOWN\nscreen: LEFT\nnull\narms: UP SIDE" +
                "\nlegs: UPDOWN\nscreen: MIDDLE\nnull\nnull\n";
        assertEquals(expected, w.toString());
    }
}
