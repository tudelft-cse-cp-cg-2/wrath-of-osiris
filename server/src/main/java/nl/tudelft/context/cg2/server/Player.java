package nl.tudelft.context.cg2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Player extends Thread {
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    private String name;

    public Player(Socket sock) throws IOException {
        this.sock = sock;
        this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        this.out = new PrintWriter(this.sock.getOutputStream(), true);
    }

    // TODO: remove this debug method
    public Player(String name) {
        this.name = name;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public String getPlayerName() {
        return this.name;
    }

    private void processInput(String clientInput) {
        if ("listlobbies".equals(clientInput)) {
            App.packLobbies().forEach(out::println);
        } else if (clientInput.startsWith("joinlobby ")) {
            int index = Integer.parseInt(clientInput.split(" ")[1]);
            this.setPlayerName(clientInput.split(" ")[2]);
            App.addPlayerToLobby(index, this).forEach(out::println);
        } else {
            System.out.println("Unknown command from client: " + clientInput);
        }
        out.println(".");
    }

    public void run() {
        String clientInput, clientOutput;
        try {
            while ((clientInput = in.readLine()) != null) {
                System.out.println(sock.getInetAddress() + ":" + sock.getPort() + "> " + clientInput);
                processInput(clientInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: could not talk to client.");
            System.exit(1);
        }
    }
}
