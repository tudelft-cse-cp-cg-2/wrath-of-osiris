
package nl.tudelft.context.cg2.server;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.context.cg2.server.game.LevelGenerator;
import nl.tudelft.context.cg2.server.game.Pose;
import nl.tudelft.context.cg2.server.game.Wall;

import java.util.ArrayList;

/**
 * The backend for the game code itself.
 */
public class GameLoop extends Thread {
    private final Lobby lobby;
    private int lives;

    /**
     * Constructor.
     *
     * @param lobby lobby that this gameloop resides in
     */
    public GameLoop(Lobby lobby) {
        this.lobby = lobby;
        this.lives = 2;
    }

    /**
     * Getter for the group's amount of lives left.
     *
     * @return the amount of lives the group has left
     */
    public int getLives() {
        return lives;
    }

    /**
     * Setter for the group's amount of lives left.
     *
     * @param lives the updated amount of lives left
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Subtracts one life from the group's amount of lives.
     */
    public void subtractLife() {
        this.lives--;
    }

    @Override
    public void run() {
        LevelGenerator generator = new LevelGenerator(lobby.getPlayers().size());
        ArrayList<Wall> level = generator.generateLevel();
        while (lobby.isStarted()) { // this loop runs once every level
            for (Player player : lobby.getPlayers()) {
                player.sendLevel(level);
            }
            int currentWallIndex = 0;
            while (currentWallIndex < level.size() && lobby.isStarted()) {
                everybodyWallReady();
                waitForFinalPoses();
                ArrayList<Pose> finalPoses = new ArrayList<>();
                for (Player player : lobby.getPlayers()) {
                    finalPoses.add(player.getFinalPose());
                    player.setFinalPose(null);
                }
                boolean avoidedCollision = level.get(currentWallIndex).compare(finalPoses);
                if (!avoidedCollision) {
                    subtractLife();
                    //Todo: use sendFailed to inform everyone which player made the mistake
                    if (lives < 0) {
                        lobby.stopGame();
                    }
                }
                for (Player player : lobby.getPlayers()) {
                    player.updateLives();
                }
                currentWallIndex++;
            }
            level = generator.generateLevel();
        }
        System.out.println("Gameloop stopped.");
    }

    /**
     * Doesn't stop until all players have reported "ready", then resets the ready variable,
     * and sends signals players to start next wall.
     */
    private void everybodyWallReady() {
        boolean everybodyWallReady = false;
        /* Checking lobby.isStarted() is necessary for when everyone left while waiting for wall
         ready. */
        while (!everybodyWallReady && lobby.isStarted()) {
            everybodyWallReady = true;
            for (Player player : lobby.getPlayers()) {
                if (player != null && !player.isReady()) {
                    everybodyWallReady = false;
                }
            }
        }
        for (Player player : lobby.getPlayers()) {
            player.setReady(false);
            player.sendNextWall();
        }
    }

    /**
     * Doesn't stop until all players have sent their final pose.
     */
    @SuppressFBWarnings(value = "UC_USELESS_VOID_METHOD", justification = "waitForFinalPoses "
            + "isn't useless. If it didn't exist, the server would not wait until the clients have "
            + "actually sent the final pose.")
    private void waitForFinalPoses() {
        boolean everyoneSentFinalPose = false;
         /* Checking lobby.isStarted() is necessary for when everyone left while waiting for final
         poses. */
        while (!everyoneSentFinalPose && lobby.isStarted()) {
            everyoneSentFinalPose = true;
            for (Player player : lobby.getPlayers()) {
                if (player != null && player.getFinalPose() == null) {
                    everyoneSentFinalPose = false;
                }
            }
        }
    }
}
