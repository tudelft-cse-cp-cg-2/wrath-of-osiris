package nl.tudelft.context.cg2.client.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for Main Menu scene.
 */
@SuppressFBWarnings()
public class MainMenuController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;
    @FXML
    private Button quitButton;

    /**
     * Handles pressing the Join Game button, and changes the scene
     * to the Lobby List scene.
     * @param actionEvent Click event.
     */
    public void pressJoinGame(ActionEvent actionEvent) {
        // TODO
    }

    /**
     * Handles pressing the Create Game button, and changes the scene
     * to the Create Game scene.
     * @param actionEvent Click event.
     */
    public void pressCreateGame(ActionEvent actionEvent) {
        // TODO
    }

    /**
     * Handles pressing the Quit button, and quits the game.
     * @param event Click event.
     */
    @FXML
    public void pressQuit(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}
