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

/**
 * A connected player.
 */
public class Player extends Thread {
    private static final String EOT = ".";
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    private Lobby lobby;
    private String playerName;
    private Pose pose;
    private Pose finalPose;

    private static final long TIMEOUT = 5000; // 5 seconds
    private long heartBeat;

    private boolean disconnected;
    private boolean playing;
    private boolean ready;

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
            this.disconnect();
        }

        this.heartBeat = System.currentTimeMillis();
        this.pose = new Pose(Arm.DOWN, Arm.DOWN, Legs.DOWN, ScreenPos.MIDDLE);
        this.finalPose = null;
        this.ready = false;
        this.disconnected = false;
        this.playing = false;
    }

    /**
     * Processes the player.
     */
    public void process() {
        if (!sock.isConnected() || System.currentTimeMillis() - heartBeat > TIMEOUT) {
            this.disconnect();
        }
    }

    /**
     * Disconnects the player.
     */
    public void disconnect() {
        if (playing) {
            leaveGame();
        }

        leaveLobby();

        try {
            this.in.close();
            this.out.close();
            this.sock.close();
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.disconnected = true;
    }

    /**
     * Join a lobby.
     * @param lobby the lobby to join.
     */
    void joinLobby(Lobby lobby) {
        this.lobby = lobby;
        this.lobby.addPlayer(this);
    }

    /**
     * Leave a lobby.
     */
    private void leaveLobby() {
        if (lobby != null) {
            lobby.leave(this);
            lobby = null;
        }
    }

    /**
     * Leave a game.
     */
    public void leaveGame() {
        sendStopGame();
        this.playing = false;
    }

    /**
     * Join a game.
     */
    public void joinGame() {
        sendStartGame();
        sendLives();
        this.playing = true;
    }

    /**
     * Responds to messages received from the player's client.
     * @param clientInput the input to process
     */
    private void respond(String clientInput) {
        heartBeat = System.currentTimeMillis();

        // respond
        if (clientInput.startsWith("joinlobby ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 3;
            String newPlayerName = split[2];
            if (Server.playerNameIsUnique(newPlayerName)) {
                this.setPlayerName(newPlayerName);
                Server.addPlayerToLobby(split[1], this);
                out.println(newPlayerName);
            } else {
                out.println(EOT);
            }
        } else if (clientInput.startsWith("fetchlobby ")) {
            String[] split = clientInput.split(" ");
            assert split.length == 2;
            String lobbyName = split[1];
            out.println(Server.fetchLobby(lobbyName));
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
            if (Server.playerNameIsUnique(playerName) && Server.lobbyNameIsUnique(newLobbyName)) {
                setPlayerName(newPlayerName);
                Lobby newLobby;
                if (split.length == 4) { // lobby with password
                    newLobby = Server.createLobby(this, newLobbyName, split[3]);
                } else { // lobby without password
                    newLobby = Server.createLobby(this, newLobbyName);
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
                    Server.packLobbies().forEach(out::println);
                    out.println(EOT);
                    break;
                case "startgame":
                    lobby.startGame();
                    break;
                case "wallready":
                    setReady(true);
                    break;
                case "disconnect":
                    disconnect();
                    break;
                case "leavegame":
                    leaveGame();
                    break;
                default:
                    System.out.println("Unknown command from client: " + clientInput);
                    break;
            }
        }
    }

    /**
     * The player network input thread run method.
     * Handles player network input.
     */
    @Override
    public void run() {
        String clientInput;
        try {
            while (!disconnected) {
                if (in.ready()) {
                    clientInput = in.readLine();
                    if (clientInput != null) {
                        System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> "
                                + clientInput);
                        respond(clientInput);
                    }
                }
            }
            System.out.println("Player terminated: " + playerName);
        } catch (IOException e) {
            System.out.println(sock.getInetAddress() + ":" + sock.getPort()
                    + " disconnected (connection lost).");
            disconnect();
        }
    }

    /**
     * Updates another player's visuals for this player.
     * @param other the other player.
     */
    public void updatePlayer(Player other) {
        out.println("updatepose " + other.getPlayerName() + " "
                + other.getPose().pack());
        System.out.println("Updated " + other.getPlayerName() + " of other poses");
    }

    /**
     * Signals the player to start the game.
     */
    public void sendStartGame() {
        out.println("startgame");
    }

    /**
     * Signals the player to stop the game.
     */
    public void sendStopGame() {
        out.println("stopgame");
    }

    /**
     * Updates the lives to the player with its current lobby's lives.
     */
    public void sendLives() {
        out.println("updatelives " + lobby.getGame().getLives());
    }

    /**
     * Sends a level to the client.
     * @param level level
     */
    public void sendLevel(ArrayList<Wall> level) {
        out.println(LevelGenerator.levelToJsonString(level));
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

    /**
     * Getter for a player's pose.
     * @return the player's current pose.
     */
    public Pose getPose() {
        return pose;
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
     * @param pose the player's final pose
     */
    public void setFinalPose(Pose pose) {
        this.finalPose = pose;
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

    /**
     * Getter for ready, a boolean that indicates whether this player is ready for the wall to come.
     * @return ready
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Setter for ready.
     * @param ready ready
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * The has disconnected boolean getter.
     * @return whether a player was disconnected or not.
     */
    public boolean hasDisconnected() {
        return disconnected;
    }

    /**
     * The is playing boolean getter.
     * @return whether a player is in game game right now.
     */
    public boolean isPlaying() {
        return playing;
    }
}
