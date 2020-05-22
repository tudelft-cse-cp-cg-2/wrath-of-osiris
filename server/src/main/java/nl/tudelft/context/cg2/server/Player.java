package nl.tudelft.context.cg2.server;

import nl.tudelft.context.cg2.server.game.Pose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A connected player.
 */
public class Player extends Thread {
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    private String playerName;
    private Pose pose = null;
    private Lobby lobby;

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

    /**
     * Responds to messages received from the player's client.
     * @param clientInput the input to process
     */
    private void respond(String clientInput) {
        if ("listlobbies".equals(clientInput)) {
            App.packLobbies().forEach(out::println);
        } else if ("leavelobby".equals(clientInput)) {
            App.removePlayerFromLobbies(this);
        } else if (clientInput.startsWith("joinlobby ")) {
            int index = Integer.parseInt(clientInput.split(" ")[1]);
            this.setPlayerName(clientInput.split(" ")[2]);
            App.addPlayerToLobby(index, this);
        } else if (clientInput.startsWith("fetchlobby ")) {
            int index = Integer.parseInt(clientInput.split(" ")[1]);
            App.fetchLobby(index).forEach(out::println);
        } else if ("startgame".equals(clientInput)) {
            lobby.startGame();
        } else {
            System.out.println("Unknown command from client: " + clientInput);
        }
        out.println(".");
    }

    /**
     * Main loop for this player, which continually listens to their messages.
     */
    public void run() {
        String clientInput;
        try {
            while (!terminate) {
                clientInput = in.readLine();
                if (clientInput == null) {
                    break;
                }
                System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> "
                        + clientInput);
                respond(clientInput);
            }
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
     * Sets the lobby for this player.
     * @param lobby the new lobby for the player
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Signals the player to start the game.
     */
    public void signalStart() {
        out.println("startgame");
    }
}
