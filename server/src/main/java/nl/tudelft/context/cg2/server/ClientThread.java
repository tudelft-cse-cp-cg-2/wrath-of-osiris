package nl.tudelft.context.cg2.server;

import java.net.Socket;

public class ClientThread extends Thread {
    private Socket sock;

    public ClientThread(Socket sock) {
        this.sock = sock;
    }

    public void run() {

    }
}
