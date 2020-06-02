package nl.tudelft.context.cg2.client.model.datastructures;

import java.util.HashMap;

/**
 * This enum represents a pose's legs, which can be in 3 different positions.
 */
public enum Legs {
    DOWN(0), LEFTUP(1), RIGHTUP(2);

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

    /**
     * Returns the index of the Legs value.
     * @return index of the Legs value
     */
    public int indexOf() {
        return this.id;
    }
}
