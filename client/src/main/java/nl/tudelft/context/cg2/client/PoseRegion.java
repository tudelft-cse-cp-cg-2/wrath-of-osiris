package nl.tudelft.context.cg2.client;

import org.opencv.core.Point;

public class PoseRegion {
    private final int topLeftX;
    private final int topLeftY;
    private final int bottomRightX;
    private final int bottomRightY;
    private final int limb; // Left/Right Arm/Leg
    private final int option; // Up/Down/Horizontal

    public PoseRegion(int tx, int ty, int bx, int by, int limb, int option) {
        topLeftX = tx;
        topLeftY = ty;
        bottomRightX = bx;
        bottomRightY = by;
        this.limb = limb;
        this.option = option;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public int getLimb() {
        return limb;
    }

    public int getOption() {
        return option;
    }

    public Point getTopLeft() {
        return new Point(topLeftX, topLeftY);
    }

    public Point getBottomRight() {
        return new Point(bottomRightX, bottomRightY);
    }

    public boolean inRange(int x, int y) {
        return (getTopLeftX() <= x && x <= getBottomRightX() && getTopLeftY() <= y && y <= getBottomRightY());
    }

}
