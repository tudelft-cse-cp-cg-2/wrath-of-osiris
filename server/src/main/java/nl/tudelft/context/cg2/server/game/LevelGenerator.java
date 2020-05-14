package nl.tudelft.context.cg2.server.game;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Everything that has to do with randomly generated things in the game resides in this class.
 */
public class LevelGenerator {
    /**
     * The following variable determines the chances for the occurrence of these scenarios:
     * Scenario 1: all players have to go through the same hole
     * Scenario 2: some players have to go through a certain hole, others are free to choose another
     * So if the value is 6: scenario 1: 1/6 chance. scenario 2: 5/6 chance
     */
    private static final int NUMBERS_ABOVE_HOLES_SCENARIO_CHANCES = 6;
    /**
     * The chance that numbers above holes appear increases every level. The following variable
     * determines by how much that chance increases per level. (0.05 = 5% increase per level)
     */
    private static final double NUMBERS_ABOVE_HOLES_CHANCE_INCREASE_PER_LEVEL = 0.05;
    private static final int AMOUNT_OF_WALLS_PER_LEVEL = 50;
    private final int amountOfPlayers;
    private int level;

    /**
     * Constructor.
     *
     * @param amountOfPlayers Total number of players
     */
    public LevelGenerator(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
        level = 1;
    }

    /**
     * Generates a random level.
     *
     * @return ArrayList of Walls that should be iterated over (to function as a level)
     */
    public ArrayList<Wall> generateLevel() {
        ArrayList<Wall> res = new ArrayList<Wall>(AMOUNT_OF_WALLS_PER_LEVEL);
        for (int i = 0; i < AMOUNT_OF_WALLS_PER_LEVEL; i++) {
            Wall w = generateWall(1);
            if (level > 1) {
                if (Math.random() < NUMBERS_ABOVE_HOLES_CHANCE_INCREASE_PER_LEVEL * level) {
                    w = attachNumbersToWall(w);
                }
            }
            res.add(i, w);
        }
        level++;
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
     * Attaches numbers restricting the amount of players that must go through a certain hole to
     * a Wall. Contains 2 possible scenarios:
     * Scenario 1: all players have to go through the same hole
     * Scenario 2: some players have to go through a certain hole, others are free to choose another
     *
     * @param w Wall that numbers should be attached to
     * @return Wall with numbers attached
     */
    private Wall attachNumbersToWall(Wall w) {
        int scenario = rand(NUMBERS_ABOVE_HOLES_SCENARIO_CHANCES - 1);
        int randomPose = rand(2);
        while (w.getPose(ScreenPos.valueOf(randomPose)) == null) {
            randomPose = rand(2);
        }
        if (scenario > 0) {
            //scenario 2
            //  -if there is 1 hole, abort. else continue
            //  -set random pose to rand(1, amountOfPlayers - 1)
            int amountOfHoles = 0;
            for (ScreenPos p : ScreenPos.values()) {
                if (p != null) {
                    amountOfHoles++;
                }
            }
            if (amountOfHoles >= 2) {
                w.setNumber(ScreenPos.valueOf(randomPose), rand(1, amountOfPlayers - 1));
                return w;
            }
        }
        //scenario 1
        //  -set random pose to amountOfPlayers
        w.setNumber(ScreenPos.valueOf(randomPose), amountOfPlayers);
        return w;
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
