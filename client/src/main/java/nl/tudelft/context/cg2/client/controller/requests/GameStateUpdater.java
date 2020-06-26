package nl.tudelft.context.cg2.client.controller.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.BackendWall;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.world.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static nl.tudelft.context.cg2.client.Settings.debugMessage;

/**
 * Updater class that syncs the group lives and poses with the server.
 */
public class GameStateUpdater extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private final Controller controller;
    private boolean started = false;
    private boolean terminate = false;

    /**
     * Constructor for GameStateUpdater.
     *
     * @param in         server input
     * @param out        server output
     * @param controller app controller
     */
    public GameStateUpdater(BufferedReader in, PrintWriter out, Controller controller) {
        this.in = in;
        this.out = out;
        this.controller = controller;
    }

    /**
     * Changes this.terminate to true, effectively telling the run function to terminate.
     */
    public void terminate() {
        this.terminate = true;
    }

    /**
     * Executes the updater.
     */
    @Override
    public void run() {
        String serverInput;
        System.out.println("Started game state updater");
        try {
            while (in.ready() && !terminate) {
                serverInput = in.readLine();
                if (serverInput == null) {
                    break;
                }

                respond(serverInput);
            }
        } catch (IOException e) {
            System.out.println("Cannot update pose as the input stream was shut down.");
        }
    }

    /**
     * Responds to incoming server messages.
     * @param serverInput message from server
     */
    private void respond(String serverInput) {
        if (serverInput.startsWith("updatelives ")) {
            updateLives(serverInput);
        } else if (serverInput.startsWith("updatepose ")) {
            updatePlayerPose(serverInput);
        } else if (serverInput.startsWith("fetchlobby ")) {
            try {
                updateLobbyNames(serverInput);
            } catch (NullPointerException e) {
                System.out.println("Lobby already left.");
            }
        } else if (serverInput.startsWith("[{")) {
            updateLevel(serverInput);
        } else if (serverInput.startsWith("playerleft ")) {
            updatePlayerLeft(serverInput);
        } else {
            switch (serverInput) {
                case "startgame":
                    Platform.runLater(() -> controller.getViewController()
                            .getGameSceneController().startGame());
                    break;
                case "stopgame":
                    Platform.runLater(() -> controller.getViewController()
                            .getGameSceneController().stopGame());
                    break;
                case "nextwall":
                    Platform.runLater(() -> {
                        try {
                            controller.getModel().getWorld().startWave();
                        } catch (NullPointerException e) {
                            System.out.println("Game already stopped.");
                        }
                    });
                    break;
                default:
                    System.out.println("Unknown command from server: " + serverInput);
                    break;
            }
        }
    }

    /**
     * Updates the game state when a player has left the running game.
     * @param serverInput the name of the player that left the running game.
     */
    private void updatePlayerLeft(String serverInput) {
        String playerName = serverInput.split(" ")[1];
        Lobby lobby = controller.getModel().getCurrentLobby();
        Player leftPlayer = null;
        for (Player player : lobby.getPlayers()) {
            if (playerName.equals(player.getName())) {
                leftPlayer = player;
                break;
            }
        }
        if (leftPlayer != null) {
            Player finalLeftPlayer = leftPlayer;
            Platform.runLater(() ->
                    controller.getModel().getWorld().onAvatarDeath(finalLeftPlayer.getAvatar()));
            lobby.removePlayer(playerName);
        } else {
            System.out.println("Left player not found: " + playerName);
        }
    }

    /**
     * Setter for the amount of lives.
     * @param serverInput the new amount of lives
     */
    private void updateLives(String serverInput) {
        int newLives = Integer.parseInt(serverInput.split(" ")[1]);

        World world = controller.getModel().getWorld();

        if (started) {
            if (world.getWallIdx() < world.getLevel().size()) {
                sendReady();
            }
        }

        if (newLives < world.getLives()) {
            controller.getView().getGameScene().getFailSound().play();
        } else {
            controller.getView().getGameScene().getWinSound().play();
        }

        Platform.runLater(() -> {
            world.setLives(newLives);
            controller.getView().getGameScene().setHearts(newLives);
        });
    }

    /**
     * Updates the pose of a player.
     *
     * @param serverInput String packet from server containing the player name and current position
     */
    private void updatePlayerPose(String serverInput) {
        String[] split = serverInput.split(" ");
        String playerName = split[1];
        String poseStr = split[2];
        Pose pose = Pose.unpack(poseStr);

        try {
            int idx = controller.getModel().getCurrentLobby().getPlayerNames().indexOf(playerName);
            Player player = controller.getModel().getCurrentLobby().getPlayers().get(idx);
            Platform.runLater(() -> player.updatePose(pose));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("Player names not yet initialized");
        }
    }

    /**
     * Updates the player names in the current lobby, and in scene.
     * Creates the lobby if when ran for the first time.
     *
     * @param serverInput String packet from server containing the player current player names
     */
    private void updateLobbyNames(String serverInput) {
        Lobby newLobby = Lobby.unpackFetchLobby(serverInput);
        List<String> playerNames = newLobby.getPlayerNames();
        Model model = controller.getModel();
        if (model.getCurrentLobby() == null) {
            model.setCurrentLobby(newLobby);
        } else {
            Platform.runLater(() -> {
                if (model.getCurrentLobby() != null) {
                    model.getCurrentLobby().setPlayers((ArrayList) newLobby.getPlayers());
                    controller.getView().getLobbyScene().setPlayerNames(playerNames);
                } else {
                    debugMessage("Player left before update completed.");
                }
            });
        }
    }

    /**
     * Signals the server to start the game for this lobby.
     * Only usable by the host of the lobby.
     */
    public void signalStart() {
        out.println("startgame");
    }

    /**
     * Signals the server to leave the running game.
     */
    public void signalLeave() {
        out.println("leavegame");
    }

    /**
     * Updates the level.
     * @param level level
     */
    public void updateLevel(String level) {
        World world = controller.getModel().getWorld();
        world.setLevel(jsonStringToLevel(level));
        sendReady();
        if (started) {
            world.setLevelIdx(world.getLevelIdx() + 1);
        } else {
            started = true;
        }
    }

    /**
     * Converts a JSON string from the server to a level, so the client can use it.
     *
     * @param str JSON string
     * @return level
     */
    public static ArrayList<BackendWall> jsonStringToLevel(String str) {
        return new Gson().fromJson(str, new TypeToken<ArrayList<BackendWall>>() {
        }.getType());
    }

    /**
     * Sends a message to the server to indicate that the player is ready for a new wall to come.
     */
    public void sendReady() {
        out.println("wallready");
    }

    /**
     * Sends the pose to the server that should be used to check collision against the hole in the
     * wall.
     * @param pose final pose
     */
    public void sendFinalPose(Pose pose) {
        out.println("finalpose " + pose.pack());
    }

    /**
     * Getter for the started boolean.
     * @return whether the game is started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Setter for the started boolean.
     * @param started updated started value
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
}
