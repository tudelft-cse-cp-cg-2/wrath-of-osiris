package nl.tudelft.context.cg2.server.game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Wall contains up to 3 possible Poses, representing the holes in the wall. It also contains a
 * HashMap that maps each screen position to a number representing the amount of players that must
 * pass through a certain hole (or, by default, null if no such number is required).
 */
public class Wall {
    private static final int MAX_NUMBER_AMOUNT = 3;
    private HashMap<ScreenPos, Pose> poses;
    private HashMap<ScreenPos, Integer> restrictions;

    /**
     * Constructor. Produces a blank wall, its poses and numbers should be set using the setters.
     */
    public Wall() {
        poses = new HashMap<>(MAX_NUMBER_AMOUNT);
        restrictions = new HashMap<>(MAX_NUMBER_AMOUNT);
        for (ScreenPos position : ScreenPos.values()) {
            restrictions.put(position, null);
            poses.put(position, null);
        }
    }

    /**
     * Sets a number at one of the three screen positions. Before using this, first make sure there
     * exists a Pose on that position.
     *
     * @param position screen position
     * @param num      number that should be set
     */
    public void setNumber(ScreenPos position, int num) {
        restrictions.put(position, num);
    }

    /**
     * Getter.
     *
     * @param position screen position
     * @return number in that position
     */
    public Integer getNumber(ScreenPos position) {
        return restrictions.get(position);
    }

    /**
     * Sets a Pose at one of the three screen positions.
     *
     * @param position screen position
     * @param pose     pose that should be set
     */
    public void setPose(ScreenPos position, Pose pose) {
        pose.setScreenPos(position);
        poses.put(position, pose);
    }

    /**
     * Getter.
     *
     * @param position screen position
     * @return pose in that position
     */
    public Pose getPose(ScreenPos position) {
        return poses.get(position);
    }

    /**
     * Compares the players' poses to the poses required for the wall.
     *
     * @param playerPoses ArrayList of the players' poses
     * @return false if a life is lost, else true
     */
    public boolean compare(ArrayList<Pose> playerPoses) {
        int[] frequency = new int[MAX_NUMBER_AMOUNT];
        for (Pose pose : playerPoses) {
            switch (pose.getScreenPos()) {
                case LEFT:
                    if (pose.equals(getPose(ScreenPos.LEFT))) {
                        frequency[0]++;
                    } else {
                        return false;
                    }
                    break;
                case MIDDLE:
                    if (pose.equals(getPose(ScreenPos.MIDDLE))) {
                        frequency[1]++;
                    } else {
                        return false;
                    }
                    break;
                case RIGHT:
                    if (pose.equals(getPose(ScreenPos.RIGHT))) {
                        frequency[2]++;
                    } else {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        for (ScreenPos position : ScreenPos.values()) {
            if (restrictions.get(position) != null) {
                if (restrictions.get(position) != frequency[position.ordinal()]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a string representation of Wall, for debugging purposes.
     * @return String representation
     */
    public String toString() {
        String res = "";
        for (ScreenPos s : ScreenPos.values()) {
            res += restrictions.get(s) + "\n" + poses.get(s) + "\n";
        }
        return res;
    }
}
