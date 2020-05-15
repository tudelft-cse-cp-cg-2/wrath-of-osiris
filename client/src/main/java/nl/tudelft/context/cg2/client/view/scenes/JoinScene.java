package nl.tudelft.context.cg2.client.view.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ListView listView;
    private ScrollPane scrollPane;

    private VBox controlsVBox;
    private TextField playerNameField;
    private SimpleButton joinButton;

    private SimpleButton backButton;

    private ObservableList<String> lobbyNames;

    /**
     * The lobby joining scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public JoinScene(Window window, Pane root) {
        super(window, root);
        lobbyNames = FXCollections.observableArrayList();
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

        listView = new ListView<String>(lobbyNames);
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
    public ObservableList<String> getLobbyNames() {
        return lobbyNames;
    }

    /**
     * Updates the displayed list of available lobbies.
     * @param newLobbies the new set of lobbies.
     */
    public void setLobbyNames(ArrayList<Lobby> newLobbies) {
        this.lobbyNames.clear();
        for (Lobby newLobby : newLobbies) {
            lobbyNames.add(newLobby.getPlayers().size() + "/5 " + newLobby.getName());
        }
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
