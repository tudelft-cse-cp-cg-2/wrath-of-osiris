
package nl.tudelft.context.cg2.server;

import nl.tudelft.context.cg2.server.game.LevelGenerator;
import nl.tudelft.context.cg2.server.game.Pose;
import nl.tudelft.context.cg2.server.game.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The backend for the game code itself.
 */
public class Game {

    /**
     * Enumerates the various game states.
     */
    public enum GameState {
        START,
        NEXT_WALL,
        FINAL_POSES,
        NEXT_LEVEL,
        ENDED,
    }

    private final Lobby lobby;
    private GameState state;
    private final LevelGenerator generator;
    private final ArrayList<Wall> level;
    private int currentWall;
    private int lives;

    /**
     * Constructor.
     * @param lobby lobby that this gameloop resides in
     */
    public Game(Lobby lobby) {
        this.lobby = lobby;
        this.generator = new LevelGenerator(lobby.getPlayers().size());
        this.level = new ArrayList<>();
        this.state = GameState.START;
        this.currentWall = 0;
        this.lives = 10;
    }

    /**
     * processes the game.
     */
    public void process() {
        switch (state) {
            case START:
            case NEXT_LEVEL:
                createLevel();
                updateLives();
                state = GameState.NEXT_WALL;
                break;
            case NEXT_WALL:
                if (wallReady()) {
                    startWave();
                }
                break;
            case FINAL_POSES:
                if (finalPosesAvailable()) {
                    finishWave();
                    updateLives();
                }
                break;
            default:
                state = GameState.ENDED;
                break;
        }

        updatePlayerFlags();
    }

    /**
     * Updates the player specific flags for all other players.
     * Mainly used to synchronize player poses.
     */
    private void updatePlayerFlags() {
        getPlayerSteam().forEach(p -> {
            if (p.isPlaying()) {
                for (Player other : getPlayers()) {
                    if (p != other) {
                        p.updatePlayer(other);
                    }
                }
            }
        });
    }

    /**
     * Creates a new level.
     */
    private void createLevel() {
        level.clear();
        level.addAll(generator.generateLevel());
        getPlayerSteam().forEach(p -> p.sendLevel(level));
        currentWall = 0;
    }

    /**
     * Updates the current lives to each player.
     */
    private void updateLives() {
        getPlayerSteam().forEach(Player::sendLives);
    }

    /**
     * Starts a new wave: makes a next wall move.
     */
    private void startWave() {
        getPlayerSteam().forEach(p -> {
            p.setReady(false);
            p.sendNextWall();
        });

        state = GameState.FINAL_POSES;
    }

    /**
     * Finishes the wall wave when a wall arrived at the players' locations.
     * This then checks if all players passed succesfully or not.
     * Updates the game state and lives accordingly.
     */
    private void finishWave() {
        ArrayList<Pose> finalPoses = new ArrayList<>();
        getPlayerSteam().forEach(p -> {
            finalPoses.add(p.getFinalPose());
            p.setFinalPose(null);
        });

        if (!level.get(currentWall).compare(finalPoses) && --lives < 0) {
            lobby.stopGame();
            state = GameState.ENDED;
            return;
        }

        currentWall++;
        state = currentWall < level.size() ? GameState.NEXT_WALL : GameState.NEXT_LEVEL;
    }

    /**
     * Checks if a wall is ready to start moving.
     * @return true if the wall is ready, false otherwise.
     */
    private boolean wallReady() {
        for (Player player : getPlayers()) {
            if (player != null && !player.isReady()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a all the final poses have been received.
     * @return true if the final poses are all available.
     */
    private boolean finalPosesAvailable() {
        for (Player player : getPlayers()) {
            if (player != null && player.getFinalPose() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a stream of players in the game.
     * @return the game player stream.
     */
    private Stream<Player> getPlayerSteam() {
        return lobby.getPlayers().stream().filter(Objects::nonNull).filter(Player::isPlaying);
    }

    /**
     * Creates a list of players in the game.
     * @return the game player list.
     */
    private List<Player> getPlayers() {
        return getPlayerSteam().collect(Collectors.toList());
    }

    /**
     * Getter for the group's amount of lives left.
     * @return the amount of lives the group has left
     */
    public int getLives() {
        return lives;
    }
}
