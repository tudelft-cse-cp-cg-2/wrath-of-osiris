package nl.tudelft.context.cg2.client.view.scenes;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

import java.util.List;

/**
 * The scene to join available lobbies.
 * Features the lobby selection menu as shown to the user.
 */
public class JoinScene extends BaseScene {

    private Text headerText;
    private HBox centerHBox;

    private ListView<String> listView;
    private ScrollPane scrollPane;

    private VBox controlsVBox;
    private TextField playerNameField;
    private SimpleButton joinButton;

    private SimpleButton backButton;

    private static final int MAX_PLAYER_NAME_LENGTH = 15;

    /**
     * The lobby joining scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public JoinScene(Window window, Pane root) {
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

        listView = new ListView<>();
        listView.setId("lobby-list");
        listView.setMinWidth(220);
        listView.setEditable(false);

        scrollPane = new ScrollPane(listView);
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

        // enforce that there are no spaces, and enforce the maximum length
        playerNameField.textProperty().addListener((obj, oldV, newV) -> {
            if (playerNameField.getText().length() > MAX_PLAYER_NAME_LENGTH) {
                String s = playerNameField.getText().substring(0, MAX_PLAYER_NAME_LENGTH);
                playerNameField.setText(s);
            }
            playerNameField.setText(playerNameField.getText().replaceAll("\\s+", ""));
        });

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
     * Updates the displayed list of available lobbies.
     * @param lobbyNames the new set of lobbies by name.
     */
    public void setLobbyNames(List<String> lobbyNames) {
        listView.setItems(FXCollections.observableArrayList(lobbyNames));
    }

    /**
     * The player name text field getter.
     * @return the player name text field.
     */
    public TextField getPlayerNameField() {
        return playerNameField;
    }

    /**
     * Getter for the lobby ListView.
     * @return ListView containing the lobbies.
     */
    public ListView getListView() {
        return listView;
    }
}
