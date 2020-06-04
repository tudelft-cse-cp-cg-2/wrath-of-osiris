package nl.tudelft.context.cg2.server;

import nl.tudelft.context.cg2.server.game.Arm;
import nl.tudelft.context.cg2.server.game.Legs;
import nl.tudelft.context.cg2.server.game.LevelGenerator;
import nl.tudelft.context.cg2.server.game.Pose;
import nl.tudelft.context.cg2.server.game.ScreenPos;
import nl.tudelft.context.cg2.server.game.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Timer;

/**
 * A connected player.
 */
public class Player extends Thread {
    private static final String EOT = ".";
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Current pose of the player.
     * This starts out with all limbs neutral, and in the middle region.
     */
    private Pose pose = new Pose(Arm.DOWN, Arm.DOWN, Legs.DOWN, ScreenPos.MIDDLE);

    /**
     * Pose that should be compared to the hole in the wall. After the comparison, it should be
     * changed back to null such that the same finalPose won't be used twice in a row.
     */
    private Pose finalPose = null;
    private boolean ready = false;
    private String playerName;
    private Lobby lobby;

    private Timer eventTimer;

    private boolean terminate = false;

    /**
     * Constructor for players.
     * @param sock the socket for the player's connection
     */
    public Player(Socket sock) {
        try {
            this.sock = sock;
            this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream(),
                    StandardCharsets.UTF_8));
            this.out = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream(),
                    StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            System.out.println(sock.getInetAddress() + ":" + sock.getPort()
                    + " disconnected (connection lost).");
            App.disconnectPlayer(this);
        }
        this.eventTimer = new Timer();
    }

    /**
     * Changes this.terminate to true, effectively telling the run function to terminate.
     */
    public void terminate() {
        this.terminate = true;
    }

    /**
     * Setter for playerName.
     * @param name playerName
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Getter for playerName.
     * @return playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Responds to messages received from the player's client.
     * @param clientInput the input to process
     */
    private void respond(String clientInput) {
        if (clientInput.startsWith("joinlobby ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 3;
            String lobbyName = split[1];
            this.setPlayerName(split[2]);
            App.addPlayerToLobby(lobbyName, this);
            out.println(EOT);
        } else if (clientInput.startsWith("fetchlobby ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 2;
            String lobbyName = split[1];
            out.println(App.fetchLobby(lobbyName));
        } else if (clientInput.startsWith("updatepose ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 2;
            String poseStr = split[1];
            this.pose = Pose.unpack(poseStr);
        } else if (clientInput.startsWith("createlobby ")) {
            String[] split = clientInput.split(" ");
            assert (split.length == 3 || split.length == 4);
            setPlayerName(split[1]);
            Lobby newLobby;

            if (split.length == 4) { // lobby with password
                newLobby = App.createLobby(this, split[2], split[3]);
            } else { // lobby without password
                newLobby = App.createLobby(this, split[2]);
            }
            out.println(newLobby.getName());
            out.println(EOT);
        } else if (clientInput.startsWith("finalpose ")) {
            String poseStr = clientInput.split(" ")[1];
            setFinalPose(Pose.unpack(poseStr));
        } else {
            switch (clientInput) {
                case "listlobbies":
                    App.packLobbies().forEach(out::println);
                    out.println(EOT);
                    break;
                case "leavelobby":
                    App.removePlayerFromLobbies(this);
                    break;
                case "startgame":
                    lobby.startGame();
                    break;
                case "wallready":
                    setReady(true);
                    break;
                default:
                    System.out.println("Unknown command from client: " + clientInput);
                    break;
            }
        }
    }

    /**
     * Main loop for this player, which continually listens to their messages,
     * and starts the updating of other player's poses to the player.
     */
    @Override
    public void run() {
        String clientInput;
        try {
            while (!terminate) {
                if(in.ready()) {
                    clientInput = in.readLine();
                    if (clientInput != null) {
                        System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> "
                                + clientInput);
                        respond(clientInput);
                    }
                }
            }
            stopPoseUpdater();
        } catch (IOException e) {
            System.out.println(sock.getInetAddress() + ":" + sock.getPort()
                    + " disconnected (connection lost).");
            App.disconnectPlayer(this);
        }
    }

    /**
     * Getter for a player's pose.
     * @return the player's current pose.
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Setter for a player's pose.
     * @param pose the player's current pose.
     */
    public void setPose(Pose pose) {
        this.pose = pose;
    }

    /**
     * Gets the lobby of the player.
     * @return lobby this player is in
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Getter for a player's final pose.
     * @return the player's final pose
     */
    public Pose getFinalPose() {
        return this.finalPose;
    }

    /**
     * Setter for a player's final pose.
     * @param p the player's final pose
     */
    public void setFinalPose(Pose p) {
        this.finalPose = p;
    }

    /**
     * Sets the lobby for this player.
     * @param lobby the new lobby for the player
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Signals the player to start the game.
     */
    public void startGame() {
        out.println("startgame");
    }

    /**
     * Signals the player to start the game.
     */
    public void stopGame() {
        stopPoseUpdater();
        out.println("stopgame");
    }

    /**
     * Updates the lives to the player with its current lobby's lives.
     */
    public void updateLives() {
        out.println("updatelives " + lobby.getGameLoop().getLives());
    }

    /**
     * Sends a level to the client.
     * @param level level
     */
    public void sendLevel(ArrayList<Wall> level) {
        out.println(LevelGenerator.levelToJsonString(level));
    }

    /**
     * Starts the pose updater for this player.
     */
    public void startPoseUpdater() {
        PoseUpdater poseUpdater = new PoseUpdater(in, out, this);
        eventTimer.schedule(poseUpdater, 500, 500);
    }

    /**
     * Stops the pose updater for this player.
     */
    public void stopPoseUpdater() {
        eventTimer.cancel();
        eventTimer.purge();
        eventTimer = null;
    }

    public void sendFailed(Player player) {
        out.println("failed " + player.getPlayerName());
    }

    public void sendNextWall() {
        out.println("nextwall");
    }
}
