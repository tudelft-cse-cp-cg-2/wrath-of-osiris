package nl.tudelft.context.cg2.client.model.datastructures;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A Wall contains up to 3 possible Poses, representing the holes in the wall. It also contains a
 * HashMap that maps each screen position to a number representing the amount of players that must
 * pass through a certain hole (or, by default, null if no such number is required).
 */
public class BackendWall {
    private static final int MAX_NUMBER_AMOUNT = 3;
    private BackendPose poseLeft;
    private BackendPose poseMiddle;
    private BackendPose poseRight;
    private int numberLeft;
    private int numberMiddle;
    private int numberRight;

    /**
     * Constructor. Produces a blank wall, its poses and numbers should be set using the setters.
     */
    public BackendWall() {
        poseLeft = null;
        poseMiddle = null;
        poseRight = null;
        numberLeft = -1;
        numberMiddle = -1;
        numberRight = -1;
    }

    /**
     * Sets a number at one of the three screen positions. Before using this, first make sure there
     * exists a Pose on that position.
     *
     * @param position screen position
     * @param num      number that should be set
     */
    public void setNumber(ScreenPos position, int num) {
        switch (position) {
            default:
            case LEFT:
                numberLeft = num;
                break;
            case MIDDLE:
                numberMiddle = num;
                break;
            case RIGHT:
                numberRight = num;
                break;
        }
    }

    /**
     * Getter.
     *
     * @param position screen position
     * @return number in that position
     */
    public Integer getNumber(ScreenPos position) {
        switch (position) {
            case LEFT:
                return numberLeft;
            case MIDDLE:
                return numberMiddle;
            case RIGHT:
                return numberRight;
            default:
                return -1;
        }
    }

    /**
     * Sets a Pose at one of the three screen positions.
     *
     * @param position screen position
     * @param pose     pose that should be set
     */
    public void setPose(ScreenPos position, BackendPose pose) {
        pose.setScreenPos(position);
        switch (position) {
            default:
            case LEFT:
                poseLeft = pose;
                break;
            case MIDDLE:
                poseMiddle = pose;
                break;
            case RIGHT:
                poseRight = pose;
                break;
        }
    }

    /**
     * Getter.
     *
     * @param position screen position
     * @return pose in that position
     */
    public BackendPose getPose(ScreenPos position) {
        switch (position) {
            default:
            case LEFT:
                return poseLeft;
            case MIDDLE:
                return poseMiddle;
            case RIGHT:
                return poseRight;
        }
    }

    /**
     * Compares the players' poses to the poses required for the wall.
     *
     * @param playerPoses ArrayList of the players' poses
     * @return false if a life is lost, else true
     */
    public boolean compare(ArrayList<BackendPose> playerPoses) {
        int[] frequency = new int[MAX_NUMBER_AMOUNT];
        for (BackendPose pose : playerPoses) {
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
        if (numberLeft != -1) {
            if (numberLeft != frequency[0]) {
                return false;
            }
        }
        if (numberMiddle != -1) {
            if (numberMiddle != frequency[1]) {
                return false;
            }
        }
        if (numberRight != -1) {
            if (numberRight != frequency[2]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BackendWall wall = (BackendWall) o;
        return numberLeft == wall.numberLeft
                && numberMiddle == wall.numberMiddle
                && numberRight == wall.numberRight
                && Objects.equals(poseLeft, wall.poseLeft)
                && Objects.equals(poseMiddle, wall.poseMiddle)
                && Objects.equals(poseRight, wall.poseRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poseLeft, poseMiddle, poseRight, numberLeft, numberMiddle, numberRight);
    }

    /**
     * Returns a string representation of Wall, for debugging purposes.
     *
     * @return String representation
     */
    public String toString() {
        String res = "";
        res += numberLeft + "\n" + poseLeft + "\n";
        res += numberMiddle + "\n" + poseMiddle + "\n";
        res += numberRight + "\n" + poseRight + "\n";
        return res;
    }
}
