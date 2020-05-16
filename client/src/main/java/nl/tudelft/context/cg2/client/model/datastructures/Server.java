package nl.tudelft.context.cg2.client.model.datastructures;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final String HOST = "localhost";
    private final int PORT = 43594;

    private Socket sock;
    public PrintWriter out;
    public BufferedReader in;

    public Server() {
    }

    public void connect() {
        try {
            this.sock = new Socket(HOST, PORT);
            this.out = new PrintWriter(sock.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error: could not connect to server.");
            System.exit(1);
        }
    }

    public Lobby joinLobby(int no, String name) {
        String fromServer;
        Lobby lobby;

        out.println("joinlobby " + no + " " + name);
        try {
            // get lobby
            fromServer = in.readLine();
            lobby = Lobby.unpackLobby(fromServer);

            // get players
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null || fromServer.equals(".")) { break; }
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
                if (fromServer == null || fromServer.equals(".")) { break; }
                System.out.println("received: " + fromServer);
                lobbies.add(Lobby.unpackLobby(fromServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lobbies;
    }
}
