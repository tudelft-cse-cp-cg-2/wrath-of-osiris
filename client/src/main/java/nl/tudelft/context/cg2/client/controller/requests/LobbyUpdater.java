package nl.tudelft.context.cg2.client.controller.requests;

import javafx.application.Platform;
import nl.tudelft.context.cg2.client.view.scenes.LobbyScene;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.TimerTask;

/**
 * Updater class that periodically fetches a lobby.
 */
public class LobbyUpdater extends TimerTask {
    private final BufferedReader in;
    private final PrintWriter out;
    private final int index;
    private final LobbyScene scene;

    /**
     * Constructor for LobbyUpdater.
     * @param in server input
     * @param out server output
     * @param index the lobby to fetch
     * @param scene the LobbyScene to be updated
     */
    public LobbyUpdater(BufferedReader in, PrintWriter out, int index, LobbyScene scene) {
        this.in = in;
        this.out = out;
        this.index = index;
        this.scene = scene;
    }

    /**
     * Executes the request.
     */
    public void run() {
        FetchLobbyRequest req = new FetchLobbyRequest(in, out, index);
        req.start();
        try {
            req.join();
            Platform.runLater(() -> scene.setPlayerNames(req.getResult().getPlayerNames()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
