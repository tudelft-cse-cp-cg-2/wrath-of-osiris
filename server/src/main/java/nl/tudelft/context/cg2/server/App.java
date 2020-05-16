package nl.tudelft.context.cg2.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final int PORT = 43594;

    private static void startServer() throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);
        System.out.println("Started server on port " + PORT);
        while (true) {
            Socket sock = serverSock.accept();
            System.out.println("Accepted new connection from " + sock.getInetAddress());

            new ClientThread(sock).start();
        }
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
