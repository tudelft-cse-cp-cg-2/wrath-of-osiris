package nl.tudelft.context.cg2.client.controller.io.posedetection;

import java.util.HashMap;

/**
 * Enumerator representing body screen positions.
 */
public enum ScreenPos {
    left(0),
    middle(1),
    right(2);

    private final int id;
    private static final HashMap<Integer, ScreenPos> MAP = new HashMap<>();

    /**
     * Constructor.
     * @param id identifier connected to the constant
     */
    ScreenPos(int id) {
        this.id = id;
    }

    static {
        for (ScreenPos position : ScreenPos.values()) {
            MAP.put(position.id, position);
        }
    }

    /**
     * Returns the value connected to the given identifier.
     *
     * @param index identifier mapped to the value
     * @return value mapped to the identifier
     */
    public static ScreenPos valueOf(int index) {
        return (ScreenPos) MAP.get(index);
    }

    /**
     * Returns the index of the ScreenPosition value.
     * @return index of the screen position value
     */
    public int indexOf() {
        return this.id;
    }
}


