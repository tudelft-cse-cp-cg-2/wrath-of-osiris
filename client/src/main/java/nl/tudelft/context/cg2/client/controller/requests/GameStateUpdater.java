package nl.tudelft.context.cg2.client.controller.requests;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.controller.Controller;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.model.datastructures.Player;
import nl.tudelft.context.cg2.client.model.datastructures.PlayerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private PlayerFactory playerFactory;

    /**
     * Constructor for GameStateUpdater.
     * @param in server input
     * @param out server output
     * @param controller app controller
     */
    public GameStateUpdater(BufferedReader in, PrintWriter out, Controller controller, PlayerFactory playerFactory) {
        this.in = in;
        this.out = out;
        this.controller = controller;
        this.playerFactory = playerFactory;
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
        if (serverInput.startsWith("updatelives ")) {
            int newLives = Integer.parseInt(serverInput.split(" ")[1]);
            Platform.runLater(() -> controller.getModel().setLives(newLives));
        } else if (serverInput.startsWith("updatepose ")) {
            updatePlayerPose(serverInput);
        } else if (serverInput.startsWith("fetchlobby ")) {
            updateLobbyNames(serverInput);
        } else {
            switch (serverInput) {
                case "startgame":
                    started = true;
                    Platform.runLater(() -> controller.getViewController()
                            .getGameSceneController().startGame());
                    break;
                case "stopgame":
                    Platform.runLater(() -> controller.getViewController()
                            .getGameSceneController().stopGame());
                    break;
                default:
                    System.out.println("Unknown command from server: " + serverInput);

            }
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

        try {
            int idx = controller.getModel().getCurrentLobby().getPlayerNames().indexOf(playerName);
            Player player = controller.getModel().getCurrentLobby().getPlayers().get(idx);
            player.updatePose(pose);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("Player names not yet initialized");
        }
    }

    /**
     * Updates the player names in the current lobby, and in scene.
     * Creates the lobby if when ran for the first time.
     * @param serverInput String packet from server containing the player current player names
     */
    private void updateLobbyNames(String serverInput) {
        Lobby newLobby = Lobby.unpackFetchLobby(serverInput, playerFactory);
        List<String> playerNames = newLobby.getPlayerNames();
        Model model = controller.getModel();
        if (model.getCurrentLobby() == null) {
            model.setCurrentLobby(newLobby);
        } else {
            Platform.runLater(() -> {
                model.getCurrentLobby().setPlayers((ArrayList) newLobby.getPlayers());
                controller.getView().getLobbyScene().setPlayerNames(playerNames);
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
     * Returns whether the game has been started.
     * @return boolean whether the game has started.
     */
    public boolean isStarted() {
        return started;
    }
}
