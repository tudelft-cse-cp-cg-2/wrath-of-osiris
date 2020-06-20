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
import java.util.TimerTask;

/**
 * A connected player.
 */
public class Player extends Thread {
    private static final String EOT = ".";
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    private long heartBeat;
    private static final long TIMEOUT = 900000; // 15 minutes

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
     *
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
        pulse();
    }

    /**
     * Updates this player's heartbeat value.
     */
    private void pulse() {
        this.heartBeat = System.currentTimeMillis();
    }

    /**
     * Returns whether or not the client is considered to have disappeared.
     * @return true if so, false otherwise
     */
    public boolean hasDisappeared() {
        return (System.currentTimeMillis() - this.heartBeat) > TIMEOUT;
    }

    /**
     * Changes this.terminate to true, effectively telling the run function to terminate.
     */
    public void terminate() {
        this.terminate = true;
    }

    /**
     * Setter for playerName.
     *
     * @param name playerName
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Getter for playerName.
     *
     * @return playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Getter for ready, a boolean that indicates whether this player is ready for the wall to come.
     *
     * @return ready
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Setter for ready.
     *
     * @param ready ready
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Responds to messages received from the player's client.
     *
     * @param clientInput the input to process
     */
    private void respond(String clientInput, Timer timeoutTimer) {
        // update heartbeat
        pulse();

        // respond
        if (clientInput.startsWith("joinlobby ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 3;
            String newPlayerName = split[2];
            if (App.playerNameIsUnique(newPlayerName)) {
                timeoutTimer.schedule(new TimeoutTask(this), 0, TIMEOUT);
                this.setPlayerName(newPlayerName);
                App.addPlayerToLobby(split[1], this);
                out.println(newPlayerName);
            } else {
                out.println(EOT);
            }
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
            String newPlayerName = split[1];
            String newLobbyName = split[2];
            if (App.playerNameIsUnique(playerName) && App.lobbyNameIsUnique(newLobbyName)) {
                timeoutTimer.schedule(new TimeoutTask(this), 0, TIMEOUT);
                setPlayerName(newPlayerName);
                Lobby newLobby;

                if (split.length == 4) { // lobby with password
                    newLobby = App.createLobby(this, newLobbyName, split[3]);
                } else { // lobby without password
                    newLobby = App.createLobby(this, newLobbyName);
                }
                out.println(newLobby.getName());
            } else {
                out.println(EOT);
            }
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
                    timeoutTimer.cancel();
                    App.removePlayerFromLobbies(this);
                    break;
                case "startgame":
                    lobby.startGame();
                    break;
                case "wallready":
                    setReady(true);
                    break;
                case "leavegame":
                    timeoutTimer.cancel();
                    stopPoseUpdater();
                    lobby.processPlayerLeave(playerName);
                    App.removePlayerFromLobbies(this);
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
        Timer timeoutTimer = new Timer();

        String clientInput;
        try {
            while (!terminate) {
                if (in.ready()) {
                    clientInput = in.readLine();
                    if (clientInput != null) {
                        System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> "
                                + clientInput);
                        respond(clientInput, timeoutTimer);
                    }
                }
            }
            timeoutTimer.cancel();
            timeoutTimer.purge();
            System.out.println("Player terminated: " + playerName);
        } catch (IOException e) {
            System.out.println(sock.getInetAddress() + ":" + sock.getPort()
                    + " disconnected (connection lost).");
            App.disconnectPlayer(this);
        }
        stopPoseUpdater();
    }

    /**
     * TimerTask to check if the player hasn't asynchronously disconnected.
     */
    private static class TimeoutTask extends TimerTask {
        private final Player player;

        /**
         * Constructor for the TimoutTask.
         * @param player the current player
         */
        TimeoutTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (player.hasDisappeared()) {
                App.disconnectPlayer(player);
            }
        }
    }

    /**
     * Getter for a player's pose.
     *
     * @return the player's current pose.
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Setter for a player's pose.
     *
     * @param pose the player's current pose.
     */
    public void setPose(Pose pose) {
        this.pose = pose;
    }

    /**
     * Gets the lobby of the player.
     *
     * @return lobby this player is in
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Getter for a player's final pose.
     *
     * @return the player's final pose
     */
    public Pose getFinalPose() {
        return this.finalPose;
    }

    /**
     * Setter for a player's final pose.
     *
     * @param p the player's final pose
     */
    public void setFinalPose(Pose p) {
        this.finalPose = p;
    }

    /**
     * Sets the lobby for this player.
     *
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
     *
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
        System.out.println("Pose updater stopped: " + playerName);
    }

    /**
     * Lets the client know which player hit the wall.
     * @param player the player that hit the wall
     */
    public void sendFailed(Player player) {
        out.println("failed " + player.getPlayerName());
    }

    /**
     * Lets the client know that the next wall can come.
     */
    public void sendNextWall() {
        out.println("nextwall");
    }

    /**
     * Signals the player that another player has left the game.
     * @param playerName the name of the player that has left
     */
    public void sendPlayerLeft(String playerName) {
        out.println("playerleft " + playerName);
    }
}
