package nl.tudelft.context.cg2.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.tudelft.context.cg2.client.App;

/**
 * Controller for Create Game scene.
 */
public class CreateGameController extends AbstractController {

    @FXML
    private Button backButton;
    @FXML
    private Button createGameButton;

    /**
     * Handles pressing the Create Game button, and creates a
     * game and makes the user join its lobby as host.
     * @param actionEvent Click event.
     */
    @FXML
    public void pressCreateGame(ActionEvent actionEvent) {
        App.getView().loadScene("fxml/Lobby.fxml");
    }

    /**
     * Handles pressing the Back button and returns to main menu.
     * @param actionEvent Click event.
     */
    @FXML
    public void pressBack(ActionEvent actionEvent) {
        App.getView().loadScene("fxml/MainMenu.fxml");
    }
}
