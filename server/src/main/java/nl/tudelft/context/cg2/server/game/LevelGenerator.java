package nl.tudelft.context.cg2.server.game;

/**
 * Everything that has to do with randomly generated things in the game resides in this class.
 */
public class LevelGenerator {
    private int amountOfPlayers;

    /**
     * Constructor.
     * @param amountOfPlayers Total number of players
     */
    public LevelGenerator(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    /**
     * Returns a randomized pose.
     * @return random pose
     */
    private Pose randomPose() {
        Arm aLeft = Arm.valueOf(rand(2));
        Arm aRight = Arm.valueOf(rand(2));
        ScreenPos screenPos = ScreenPos.valueOf(rand(2));
        Legs legs = Legs.valueOf(rand(2));
        return new Pose(aLeft, aRight, legs, screenPos);
    }

    /**
     * Generates a random integer ranging between 0 and maxValue.
     * @param maxValue highest possible value
     * @return random integer
     */
    protected int rand(int maxValue) {
        return (int) Math.floor(Math.random() * (maxValue + 1));
    }
}
