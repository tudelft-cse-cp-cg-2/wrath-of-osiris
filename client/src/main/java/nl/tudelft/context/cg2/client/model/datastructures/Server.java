package nl.tudelft.context.cg2.client.model.datastructures;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Representation of the game server.
 */
public class Server {
    private static final String HOST = "localhost";
    private static final int PORT = 43594;

    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Constructor for servers.
     */
    public Server() {
    }

    /**
     * Connect to the server.
     */
    @SuppressFBWarnings(value = "DM_EXIT", justification = "If we cannot connect, we cannot play.")
    public void connect() {
        try {
            this.sock = new Socket(HOST, PORT);
            this.out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(),
                    StandardCharsets.UTF_8), true);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error: could not connect to server.");
            System.exit(1);
        }
    }

    /**
     * Join a lobby.
     * @param no lobby index to join
     * @param name player name to use
     * @return the joined lobby, as received from the server
     */
    @SuppressFBWarnings(value = "DM_EXIT",
            justification = "Server is sending garbage, no use in going on.")
    public Lobby joinLobby(int no, String name) {
        String fromServer;
        Lobby lobby;

        if (no == -1) {
            no = 0;
        }

        out.println("joinlobby " + no + " " + name);
        try {
            // get lobby
            fromServer = in.readLine();
            if (fromServer == null) {
                System.exit(1);
            }
            lobby = Lobby.unpackLobby(fromServer);

            // get players
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null || fromServer.equals(".")) {
                    break;
                }
                System.out.println("received: " + fromServer);
                lobby.addPlayer(new Player(fromServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lobby;
    }

    /**
     * Request the server to leave the lobby.
     */
    public void leaveLobby() {
        out.println("leavelobby");
    }

    /**
     * Request and parse a simple list of lobbies from the server.
     * @return a list of lobbies, with their respective amounts of players
     */
    public ArrayList<Lobby> listLobbies() {
        String fromServer;
        ArrayList<Lobby> lobbies = new ArrayList<>();

        out.println("listlobbies");
        try {
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null || fromServer.equals(".")) {
                    break;
                }
                System.out.println("received: " + fromServer);
                lobbies.add(Lobby.unpackLobby(fromServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lobbies;
    }
}
