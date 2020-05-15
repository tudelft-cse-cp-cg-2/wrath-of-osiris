package nl.tudelft.context.cg2.client;

import org.opencv.core.Point;

/**
 * Class representing a square region on an image where a limb needs to be for a
 * specific pose.
 */
public class PoseRegion {
    private final int topLeftX;
    private final int topLeftY;
    private final int bottomRightX;
    private final int bottomRightY;
    private final int limb; // Left/Right Arm/Leg
    private final int option; // Up/Down/Horizontal

    /**
     * Initializer for PoseRegion.
     * @param tx - left x coordinate
     * @param ty - top y coordinate
     * @param bx - right x coordinate
     * @param by - bottom y coordinate
     * @param limb - limb of the pose
     * @param option - pose of the limb
     */
    public PoseRegion(int tx, int ty, int bx, int by, int limb, int option) {
        topLeftX = tx;
        topLeftY = ty;
        bottomRightX = bx;
        bottomRightY = by;
        this.limb = limb;
        this.option = option;
    }

    /**
     * Getter for topLeftX.
     * @return topLeftX.
     */
    public int getTopLeftX() {
        return topLeftX;
    }

    /**
     * Getter for topLeftY.
     * @return topLeftY.
     */
    public int getTopLeftY() {
        return topLeftY;
    }

    /**
     * Getter for bottomRightX.
     * @return bottomRightX.
     */
    public int getBottomRightX() {
        return bottomRightX;
    }

    /**
     * Getter for bottomRightY.
     * @return bottomRightY.
     */
    public int getBottomRightY() {
        return bottomRightY;
    }

    /**
     * Getter for limb.
     * @return limb.
     */
    public int getLimb() {
        return limb;
    }

    /**
     * Getter for option.
     * @return option.
     */
    public int getOption() {
        return option;
    }

    /**
     * Getter for the top left coordinates.
     * @return a new point with the coordinates of the top left.
     */
    public Point getTopLeft() {
        return new Point(topLeftX, topLeftY);
    }

    /**
     * Getter for the bottom right coordinates.
     * @return a new point with the coordinates of the bottom right.
     */
    public Point getBottomRight() {
        return new Point(bottomRightX, bottomRightY);
    }

    /**
     * Checks whether or not a point is inside the PoseRegion.
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if (x, y) is in the region, false otherwise
     */
    public boolean inRange(int x, int y) {
        return (getTopLeftX() <= x && x <= getBottomRightX()
                && getTopLeftY() <= y && y <= getBottomRightY());
    }

}
