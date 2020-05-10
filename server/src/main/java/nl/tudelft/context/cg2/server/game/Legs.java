package nl.tudelft.context.cg2.server.game;

import java.util.HashMap;

/**
 * This enum represents a pose's legs, which can be in 3 different positions.
 */
public enum Legs {
    DOWNDOWN(0), UPDOWN(1), DOWNUP(2);

    private final int id;
    private static final HashMap<Integer, Legs> MAP = new HashMap<>();

    /**
     * Constructor.
     * @param id identifier connected to the constant
     */
    Legs(int id) {
        this.id = id;
    }

    static {
        for (Legs legs : Legs.values()) {
            MAP.put(legs.id, legs);
        }
    }

    /**
     * Returns the value connected to the given identifier.
     *
     * @param index identifier mapped to the value
     * @return value mapped to the identifier
     */
    public static Legs valueOf(int index) {
        return (Legs) MAP.get(index);
    }
}
