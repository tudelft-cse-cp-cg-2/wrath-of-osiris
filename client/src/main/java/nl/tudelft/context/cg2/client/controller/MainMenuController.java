package nl.tudelft.context.cg2.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    public Button joinGameButton;
    @FXML
    public Button createGameButton;
    @FXML
    public Button quitButton;

    /**
     * Handles pressing the Quit button.
     * @param event click event.
     */
    @FXML
    public void pressQuit(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

}
