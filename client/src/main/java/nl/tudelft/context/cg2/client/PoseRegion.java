package nl.tudelft.context.cg2.client;

public class PoseRegion {
    private final int topLeftX;
    private final int topLeftY;
    private final int bottomRightX;
    private final int bottomRightY;

    public PoseRegion(int tx, int ty, int bx, int by) {
        topLeftX = tx;
        topLeftY = ty;
        bottomRightX = bx;
        bottomRightY = by;
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
}
