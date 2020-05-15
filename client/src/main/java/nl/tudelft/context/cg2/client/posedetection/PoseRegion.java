package nl.tudelft.context.cg2.client.posedetection;

import org.opencv.core.Point;

/**
 * Class representing a square region on an image where a limb needs to be for a
 * specific pose.
 */
public class PoseRegion {
    private final int leftX;
    private final int topY;
    private final int rightX;
    private final int bottomY;
    private final Limb limb; // Left/Right Arm/Leg
    private final Position position; // Up/Down/Horizontal

    /**
     * Initializer for PoseRegion.
     * @param tx - left x coordinate
     * @param ty - top y coordinate
     * @param bx - right x coordinate
     * @param by - bottom y coordinate
     * @param limb - limb of the pose
     * @param position - pose of the limb
     */
    public PoseRegion(int tx, int ty, int bx, int by, Limb limb, Position position) {
        leftX = tx;
        topY = ty;
        rightX = bx;
        bottomY = by;
        this.limb = limb;
        this.position = position;
    }

    /**
     * Getter for leftX.
     * @return leftX.
     */
    public int getLeftX() {
        return leftX;
    }

    /**
     * Getter for topY.
     * @return topY.
     */
    public int getTopY() {
        return topY;
    }

    /**
     * Getter for rightX.
     * @return rightX.
     */
    public int getRightX() {
        return rightX;
    }

    /**
     * Getter for bottomY.
     * @return bottomY.
     */
    public int getBottomY() {
        return bottomY;
    }

    /**
     * Getter for limb.
     * @return limb.
     */
    public Limb getLimb() {
        return limb;
    }

    /**
     * Getter for position.
     * @return position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Getter for the top left coordinates.
     * @return a new point with the coordinates of the top left.
     */
    public Point getTopLeft() {
        return new Point(leftX, topY);
    }

    /**
     * Getter for the bottom right coordinates.
     * @return a new point with the coordinates of the bottom right.
     */
    public Point getBottomRight() {
        return new Point(rightX, bottomY);
    }

    /**
     * Checks whether or not a point is inside the PoseRegion.
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if (x, y) is in the region, false otherwise
     */
    public boolean inRange(int x, int y) {
        return (getLeftX() <= x && x <= getRightX()
                && getTopY() <= y && y <= getBottomY());
    }

}
