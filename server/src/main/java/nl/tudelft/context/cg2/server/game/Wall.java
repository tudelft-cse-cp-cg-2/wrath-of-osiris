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
    private Pose left;
    private Pose middle;
    private Pose right;
    private HashMap<ScreenPos, Integer> restrictions;

    /**
     * Constructor.
     * @param left left pose
     * @param middle middle pose
     * @param right right pose
     */
    public Wall(Pose left, Pose middle, Pose right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        restrictions = new HashMap<>(MAX_NUMBER_AMOUNT);
        for (ScreenPos position : ScreenPos.values()) {
            restrictions.put(position, null);
        }
    }

    /**
     * Sets a number at one of the three screen positions.
     * @param position screen position
     * @param num number that should be set
     */
    public void setNumber(ScreenPos position, int num) {
        restrictions.put(position, num);
    }

    /**
     * Compares the players' poses to the poses required for the wall.
     *
     * @param poses ArrayList of the players' poses
     * @return false if a life is lost, else true
     */
    public boolean compare(ArrayList<Pose> poses) {
        int[] frequency = new int[MAX_NUMBER_AMOUNT];
        for (Pose pose : poses) {
            switch (pose.getScreenPos()) {
                case LEFT:
                    if (pose.equals(left)) {
                        frequency[0]++;
                    } else {
                        return false;
                    }
                    break;
                case MIDDLE:
                    if (pose.equals(middle)) {
                        frequency[1]++;
                    } else {
                        return false;
                    }
                    break;
                case RIGHT:
                    if (pose.equals(right)) {
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
}
