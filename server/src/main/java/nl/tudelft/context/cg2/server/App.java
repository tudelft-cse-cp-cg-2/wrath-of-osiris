package nl.tudelft.context.cg2.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final int PORT = 43594;

    private static ArrayList<Lobby> lobbies;
    private static ServerSocket serverSock;

    private static void startServer() throws IOException {

        serverSock = new ServerSocket(PORT);
        lobbies = new ArrayList<Lobby>();

        // TODO: remove these lines
        lobbies.add(new Lobby("test","",new ArrayList<Player>()));
        lobbies.get(0).addPlayer(new Player("jan"));
        lobbies.get(0).addPlayer(new Player("piet"));
        lobbies.add(new Lobby("one","",new ArrayList<>()));

        System.out.println("Started server on port " + PORT);
        while (true) {
            Socket sock = serverSock.accept();
            System.out.println("Accepted new connection from " + sock.getInetAddress());

            new Player(sock).start();
        }
    }

    public static List<String> addPlayerToLobby(int index, Player player) {
        List<String> out = new ArrayList<>();

        if (index >= lobbies.size()) {
            System.out.println("No such lobby");
        } else {
            Lobby lobby = lobbies.get(index);
            lobby.addPlayer(player);
            out.add(lobby.pack());
            lobby.getPlayers().forEach(x -> out.add(x.getPlayerName()));
        }

        return out;
    }

    public static List<String> packLobbies() {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < lobbies.size(); i++) {
            out.add(lobbies.get(i).pack());
        }
        return out;
    }

    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not start server");
        }
    }
}
