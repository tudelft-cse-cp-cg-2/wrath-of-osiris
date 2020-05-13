package nl.tudelft.context.cg2.server.game;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Everything that has to do with randomly generated things in the game resides in this class.
 */
public class LevelGenerator {
    private static final int AMOUNT_OF_WALLS_PER_LEVEL = 50;
    private int amountOfPlayers;
//    private int level;

    /**
     * Constructor.
     *
     * @param amountOfPlayers Total number of players
     */
    public LevelGenerator(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
//        level = 1;
    }

    /**
     * Temporary getter to suppress spotbugs warning.
     *
     * @return amount of players
     */
    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    /**
     * Generates a random level.
     *
     * @return ArrayList of Walls that should be iterated over (to function as a level)
     */
    public ArrayList<Wall> generateLevel() {
        ArrayList<Wall> res = new ArrayList<Wall>(AMOUNT_OF_WALLS_PER_LEVEL);
        for (int i = 0; i < AMOUNT_OF_WALLS_PER_LEVEL; i++) {
            res.add(i, generateWall(1));
        }
        return res;
    }

    /**
     * Generates a random Wall.
     *
     * @param minimumPoses minimum amount of Poses that should be in the Wall
     * @return randomly generated Wall
     */
    public Wall generateWall(int minimumPoses) {
        int amountOfPoses = randomAmountOfPoses(minimumPoses);
        Wall res = new Wall();
        HashSet<Integer> usedPositions = new HashSet<>(3);
        for (int i = 0; i < amountOfPoses; i++) {
            Pose p;
            int randomPos = rand(2);
            while (usedPositions.contains(randomPos)) {
                randomPos = rand(2);
            }
            p = randomPose();
            p.setScreenPos(ScreenPos.valueOf(randomPos));
            res.setPose(ScreenPos.valueOf(randomPos), p);
            usedPositions.add(randomPos);
        }
        return res;
    }

    /**
     * Returns a randomized amount of Poses to be used within a wall.
     *
     * @param minimumPoses there should be this many Poses or more
     * @return randomized amount of Poses
     */
    private int randomAmountOfPoses(int minimumPoses) {
        switch (minimumPoses) {
            case 1:
            default:
                return rand(2) + 1;
            case 2:
                return rand(1) + 2;
            case 3:
                return 3;
        }
    }

    /**
     * Returns a randomized pose.
     *
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
     *
     * @param maxValue highest possible value
     * @return random integer
     */
    protected int rand(int maxValue) {
        return rand(0, maxValue);
    }

    /**
     * Generates a random integer ranging between minValue and maxValue (including minValue and
     * maxValue).
     *
     * @param minValue lowest possible value
     * @param maxValue highest possible value
     * @return random integer
     */
    protected int rand(int minValue, int maxValue) {
        return (int) (Math.random() * (++maxValue - minValue)) + minValue;
    }
}
