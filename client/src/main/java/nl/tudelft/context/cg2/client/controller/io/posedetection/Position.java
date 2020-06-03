package nl.tudelft.context.cg2.client.controller.io.posedetection;

import java.util.HashMap;

/**
 * Enumerator representing limb positions.
 */
public enum Position {
    // arms
    top(0),
    middle(1),
    bottom(2),

    // legs
    neutral(0),
    raised(1);

    private final int id;
    private static final HashMap<Integer, Position> MAP = new HashMap<>();

    /**
     * Constructor.
     * @param id identifier connected to the constant
     */
    Position(int id) {
        this.id = id;
    }

    static {
        for (Position position : Position.values()) {
            MAP.put(position.id, position);
        }
    }

    /**
     * Returns the index of the Position value.
     * @return index of the position value
     */
    public int indexOf() {
        return this.id;
    }
}


