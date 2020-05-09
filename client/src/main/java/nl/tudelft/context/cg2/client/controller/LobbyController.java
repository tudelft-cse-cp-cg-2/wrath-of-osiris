package nl.tudelft.context.cg2.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.tudelft.context.cg2.client.App;

/**
 * Controller for Lobby scene.
 */
public class LobbyController extends AbstractController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;
    @FXML
    private Button quitButton;

    /**
     * Handles pressing the Start Game button for host,
     * and starts the game.
     * @param actionEvent Click event.
     */
    @FXML
    public void pressStartGame(ActionEvent actionEvent) {
        // TODO
    }

    /**
     * Handles pressing the Leave button, and returns to main menu.
     * @param event Click event.
     */
    @FXML
    public void pressLeave(ActionEvent event) {
        App.getView().loadScene("fxml/MainMenu.fxml");
    }
}
