package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

/**
 * The create game scene.
 * Features the main menu UI as shown to the user.
 */
public class CreateGameScene extends BaseScene {

    private Text headerText;
    private VBox centerVBox;

    private TextField playerNameField;
    private TextField lobbyNameField;
    private TextField passwordField;
    private SimpleButton createGameButton;
    private SimpleButton leaveButton;

    /**
     * The menu scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public CreateGameScene(Window window, Pane root) {
        super(window, root);
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        this.getStylesheets().add("/css/createGame.css");

        headerText = new Text("Hole in the Wall");
        headerText.setId("header-text");
        headerText.setTranslateY(20);
        StackPane.setAlignment(headerText, Pos.TOP_CENTER);

        centerVBox = new VBox();
        centerVBox.setSpacing(25);
        centerVBox.setMaxSize(50, 0);

        Label playerNameLabel = new Label();
        playerNameLabel.setLabelFor(playerNameField);
        playerNameLabel.setText("Player name:");
        playerNameLabel.getStyleClass().add("label");
        playerNameField = new TextField();
        playerNameField.setPromptText("Player name");
        playerNameField.getStyleClass().add("text-box");
        Label lobbyNameLabel = new Label();
        lobbyNameLabel.setLabelFor(lobbyNameField);
        lobbyNameLabel.setText("Lobby name:");
        lobbyNameLabel.getStyleClass().add("label");
        lobbyNameField = new TextField();
        lobbyNameField.setPromptText("Lobby name");
        lobbyNameField.getStyleClass().add("text-box");
        Label passwordLabel = new Label();
        passwordLabel.setLabelFor(passwordField);
        passwordLabel.setText("Password:");
        passwordLabel.getStyleClass().add("label");
        passwordField = new TextField();
        passwordField.setPromptText("Password (optional)");
        passwordField.getStyleClass().add("text-box");

        createGameButton = new SimpleButton("Create Game");
        createGameButton.setSize(220, 80);

        leaveButton = new SimpleButton("Back");
        leaveButton.setId("leave-button");
        leaveButton.setSize(80, 60);
        leaveButton.setTranslateX(-30);
        leaveButton.setTranslateY(-30);
        StackPane.setAlignment(leaveButton, Pos.BOTTOM_RIGHT);

        centerVBox.getChildren().addAll(playerNameLabel, playerNameField, lobbyNameLabel,
                lobbyNameField, passwordLabel, passwordField, createGameButton);
        root.getChildren().addAll(centerVBox, leaveButton, headerText);
    }

    /**
     * Animates the scene.
     */
    @Override
    public void animate() {

    }

    /**
     * Event thrown when the window is resized.
     */
    @Override
    public void onResized() {

    }

    /**
     * Event thrown when the scene is shown in the window.
     */
    @Override
    public void onShown() {

    }

    /**
     * The create game getter.
     * @return the create game button.
     */
    public SimpleButton getCreateGameButton() {
        return createGameButton;
    }

    /**
     * The leave button getter.
     * @return the leave button.
     */
    public SimpleButton getLeaveButton() {
        return leaveButton;
    }


    /**
     * The lobby name text field getter.
     * @return the lobby name text field.
     */
    public TextField getLobbyNameField() {
        return lobbyNameField;
    }

    /**
     * The password text field getter.
     * @return the password text field.
     */
    public TextField getPasswordField() {
        return passwordField;
    }

    /**
     * The player name text field getter.
     * @return the player name text field.
     */
    public TextField getPlayerNameField() {
        return playerNameField;

    }
}
