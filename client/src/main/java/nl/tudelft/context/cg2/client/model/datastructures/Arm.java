package nl.tudelft.context.cg2.client.model.datastructures;

import java.util.HashMap;

/**
 * This enum represents a pose's arm, which can be in 3 different positions.
 */
public enum Arm {
    UP(0), SIDE(1), DOWN(2);

    private final int id;
    private static final HashMap<Integer, Arm> MAP = new HashMap<>();

    /**
     * Constructor.
     *
     * @param id identifier connected to the constant
     */
    Arm(int id) {
        this.id = id;
    }

    static {
        for (Arm arm : Arm.values()) {
            MAP.put(arm.id, arm);
        }
    }

    /**
     * Returns the value connected to the given identifier.
     *
     * @param index identifier mapped to the value
     * @return value mapped to the identifier
     */
    public static Arm valueOf(int index) {
        return (Arm) MAP.get(index);
    }

    /**
     * Returns the index of the Arm value.
     *
     * @return index of the arm value
     */
    public int indexOf() {
        return this.id;
    }
}
