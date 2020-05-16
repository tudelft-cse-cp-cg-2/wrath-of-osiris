package nl.tudelft.context.cg2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A connected player.
 */
public class Player extends Thread {
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    private String playerName;

    /**
     * Constructor for players.
     * @param sock the socket for the player's connection
     * @throws IOException when the connection is interrupted
     */
    public Player(Socket sock) throws IOException {
        this.sock = sock;
        this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        this.out = new PrintWriter(this.sock.getOutputStream(), true);
    }

    // TODO: remove this debug method
    public Player(String playerName) {
        this.playerName = playerName;
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
            App.addPlayerToLobby(index, this).forEach(out::println);
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
            while ((clientInput = in.readLine()) != null) {
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
}
