package nl.tudelft.context.cg2.client.controller.requests;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.logic.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
     * @param in server input
     * @param out server output
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
    public void run() {
        String serverInput;
        System.out.println("Started game state updater");
        try {
            while (!terminate) {
                serverInput = in.readLine();
                if (serverInput == null) {
                    break;
                }

                respond(serverInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responds to incoming server messages.
     * @param serverInput message from server
     */
    private void respond(String serverInput) {
        System.out.println(serverInput);
        if ("startgame".equals(serverInput)) {
            started = true;
            Platform.runLater(() -> controller.startGame());
        } else if (serverInput.startsWith("updateLives ")) {
            int newLives = Integer.parseInt(serverInput.split(" ")[1]);
            Platform.runLater(() -> controller.getModel().setLives(newLives));
        } else if ("stopgame".equals(serverInput)) {
            // todo: Maybe how "game over" screen and summary?
            Platform.runLater(() -> controller.stopGame());
        } else if (serverInput.startsWith("updatepose ")) {
            updatePlayerPose(serverInput);
        } else if (serverInput.startsWith("fetchlobby ")) {
            updateLobbyNames(serverInput);
        } else {
            System.out.println("Unknown command from server: " + serverInput);
        }
    }

    /**
     * Updates the pose of a player.
     * @param serverInput String packet from server containing the player name and current position
     */
    private void updatePlayerPose(String serverInput) {
        String[] split = serverInput.split(" ");
        String playerName = split[1];
        String poseStr = split[2];
        Pose pose = Pose.unpack(poseStr);
        controller.updatePose(playerName, pose);
    }

    /**
     * Updates the player names in the current lobby.
     * @param serverInput String packet from server containing the player current player names
     */
    private void updateLobbyNames(String serverInput) {
        controller.getModel().setCurrentLobby(Lobby.unpackFetchLobby(serverInput));
        List<String> playerNames = controller.getModel().getCurrentLobby().getPlayerNames();
        Platform.runLater(() -> {
            controller.getView().getLobbyScene().setPlayerNames(playerNames);
        });
    }

    /**
     * Signals the server to start the game for this lobby.
     * Only usable by the host of the lobby.
     */
    public void signalStart() {
        out.println("startgame");
    }

    /**
     * Returns whether the game has been started.
     * @return boolean whether the game has started.
     */
    public boolean isStarted() {
        return started;
    }
}
