package nl.tudelft.context.cg2.client.view.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.model.datastructures.Lobby;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

import java.util.ArrayList;

/**
 * The scene to join available lobbies.
 * Features the lobby selection menu as shown to the user.
 */
public class JoinScene extends BaseScene {

    private Text headerText;
    private HBox centerHBox;

    private VBox lobbyListVBox;
    private ScrollPane scrollPane;

    private VBox controlsVBox;
    private TextField playerNameField;
    private SimpleButton joinButton;

    private SimpleButton backButton;

    private ObservableList<HBox> lobbyEntries;

    /**
     * The lobby joining scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public JoinScene(Window window, Pane root) {
        super(window, root);
        lobbyEntries = FXCollections.observableArrayList();
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

        lobbyListVBox = new VBox();
        lobbyListVBox.setId("lobby-list");
        lobbyListVBox.setMinWidth(220);

        // Example lobby.
        lobbyEntries.add(new HBox(new Label("ExampleLobby")));
        for (HBox lobby : lobbyEntries) {
            lobby.getStyleClass().add("lobby-entry");
            lobby.setMinWidth(200);
            lobby.setMaxWidth(200);
            lobbyListVBox.getChildren().add(lobby);
        }

        scrollPane = new ScrollPane(lobbyListVBox);
        scrollPane.setId("scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinWidth(220);
        scrollPane.setMinHeight(300);

        Label playerNameLabel = new Label();
        playerNameLabel.setLabelFor(playerNameField);
        playerNameLabel.setText("Provide your player name:");
        playerNameLabel.getStyleClass().add("label");
        playerNameField = new TextField();
        playerNameField.setPromptText("Player name");
        playerNameField.getStyleClass().add("text-box");

        joinButton = new SimpleButton("Join Lobby");
        joinButton.setSize(220, 80);

        controlsVBox = new VBox(playerNameLabel, playerNameField, joinButton);
        controlsVBox.setSpacing(25);

        centerHBox = new HBox(scrollPane, controlsVBox);
        centerHBox.setSpacing(70);
        centerHBox.setMaxSize(50, 0);


        backButton = new SimpleButton("Back");
        backButton.setId("leave-button");
        backButton.setSize(80, 60);
        backButton.setTranslateX(-30);
        backButton.setTranslateY(-30);
        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);

        root.getChildren().addAll(centerHBox, backButton, headerText);
    }

    /**
     * Animates the scene.
     */
    @Override
    public void animate() {

    }

    /**
     * Event thrown when the scene is shown in the window.
     */
    @Override
    public void onShown() {

    }

    /**
     * The join lobby button getter.
     * @return the join lobby button.
     */
    public SimpleButton getJoinButton() {
        return joinButton;
    }

    /**
     * The back button getter.
     * @return the back button.
     */
    public SimpleButton getBackButton() {
        return backButton;
    }

    /**
     * Lobby list getter.
     * @return list of available lobbies.
     */
    public ObservableList<HBox> getLobbyEntries() {
        return lobbyEntries;
    }

    /**
     * Updates the displayed list of available lobbies.
     * @param newLobbies the new set of lobbies.
     */
    public void setLobbyEntries(ArrayList<Lobby> newLobbies) {
        this.lobbyEntries.clear();
        for (Lobby newLobby : newLobbies) {
            lobbyEntries.add(new HBox(new Label(newLobby.getName())));
        }
    }

    /**
     * The player name text field getter.
     * @return the player name text field.
     */
    public TextField getPlayerNameField() {
        return playerNameField;
    }
}
