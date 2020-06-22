package nl.tudelft.context.cg2.server.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Everything that has to do with randomly generated things in the game resides in this class.
 */
public class LevelGenerator {
    /**
     * The chance that numbers above holes appear increases every level. The following variable
     * determines by how much that chance increases per level. (0.05 = 5% increase per level)
     */
    private static final double NUMBERS_ABOVE_HOLES_CHANCE_INCREASE_PER_LEVEL = 0.15;
    /**
     * Denotes when the aforementioned chance stops increasing. (0.60 = stops at 60%)
     */
    private static final double NUMBERS_ABOVE_HOLES_CHANCE_CAP = 0.60;
    private static final int AMOUNT_OF_WALLS_PER_LEVEL = 10;
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
                double chance = NUMBERS_ABOVE_HOLES_CHANCE_INCREASE_PER_LEVEL * level;
                if (chance > NUMBERS_ABOVE_HOLES_CHANCE_CAP) {
                    chance = NUMBERS_ABOVE_HOLES_CHANCE_CAP;
                }
                if (Math.random() < chance) {
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
    private Wall generateWall(int minimumPoses) {
        int amountOfPoses = randomAmountOfPoses(minimumPoses);
        Wall res = new Wall();
        HashSet<Integer> usedPositions = new HashSet<>(3);
        for (int i = 0; i < amountOfPoses; i++) {
            Pose p;
            int randomPos = rand(2);
            while (usedPositions.contains(randomPos)) {
                randomPos = rand(2);
            }
            p = randomPose(level > 1);
            p.setScreenPos(ScreenPos.valueOf(randomPos));
            res.setPose(ScreenPos.valueOf(randomPos), p);
            usedPositions.add(randomPos);
        }
        return res;
    }

    /**
     * Attaches numbers restricting the amount of players that must go through a certain hole to
     * a Wall. Its range is as follows:
     * Level 1: no numbers
     * Level 2: 1
     * Level 3: 1-2
     * Level 4: 1-3
     *
     * @param w Wall that numbers should be attached to
     * @return Wall with numbers attached
     */
    private Wall attachNumbersToWall(Wall w) {
        // pick a random pose index, and make sure there's a hole in its location
        int randomPose = rand(2);
        while (w.getPose(ScreenPos.valueOf(randomPose)) == null) {
            randomPose = rand(2);
        }
        // -if there is 1 hole, abort. else continue
        // -set random pose to rand(1, maxNumber)
        // maxNumber = (level - 1, but it can't go higher than amountOfPlayers)
        int maxNumber = level - 1;
        if (maxNumber > amountOfPlayers) {
            maxNumber = amountOfPlayers;
        }

        int amountOfHoles = 0;
        for (ScreenPos p : ScreenPos.values()) {
            if (w.getPose(p) != null) {
                amountOfHoles++;
            }
        }
        if (amountOfHoles >= 2) {
            w.setNumber(ScreenPos.valueOf(randomPose), rand(1, maxNumber));
        }
        return w;
    }

    /**
     * Returns a randomized pose.
     *
     * @param randomizeLegs if false, only generates poses with both legs down
     * @return random pose
     */
    private Pose randomPose(boolean randomizeLegs) {
        Arm aLeft = Arm.valueOf(rand(2));
        Arm aRight = Arm.valueOf(rand(2));
        ScreenPos screenPos = ScreenPos.valueOf(rand(2));
        Legs legs = Legs.DOWN;
        if (randomizeLegs) {
            legs = Legs.valueOf(rand(2));
        }
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
    private int rand(int maxValue) {
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
    private int rand(int minValue, int maxValue) {
        return (int) (Math.random() * (++maxValue - minValue)) + minValue;
    }

    /**
     * Converts a level to a JSON string, which can be sent to the client.
     * @param level level
     * @return JSON string
     */
    public static String levelToJsonString(ArrayList<Wall> level) {
        return new Gson().toJson(level);
    }

    /**
     * Converts a JSON string from the server to a level, so the client can use it.
     * @param str JSON string
     * @return level
     */
    public static ArrayList<Wall> jsonStringToLevel(String str) {
        return new Gson().fromJson(str, new TypeToken<ArrayList<Wall>>() {
        }.getType());
    }
}
