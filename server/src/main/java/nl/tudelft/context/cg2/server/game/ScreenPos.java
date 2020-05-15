package nl.tudelft.context.cg2.server.game;

import java.util.HashMap;

/**
 * This enum represents the position of a pose on-screen, which has 3 different possibilities.
 */
public enum ScreenPos {
    LEFT(0), MIDDLE(1), RIGHT(2);

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
        for (ScreenPos screenPos : ScreenPos.values()) {
            MAP.put(screenPos.id, screenPos);
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
}
