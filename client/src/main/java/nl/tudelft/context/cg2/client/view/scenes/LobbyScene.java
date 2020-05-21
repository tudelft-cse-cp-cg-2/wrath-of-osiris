package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;
import nl.tudelft.context.cg2.client.view.elements.etc.ListEntry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The lobby scene.
 * Features the lobby UI as shown to the user.
 */
public class LobbyScene extends BaseScene {

    private Text headerText;
    private HBox centerHBox;

    private VBox playerListVBox;
    private VBox controlsVBox;

    private Label waitMessage;
    private SimpleButton startButton;
    private SimpleButton leaveButton;

    /**
     * The lobby scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public LobbyScene(Window window, Pane root) {
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

        playerListVBox = new VBox();
        playerListVBox.setId("player-list");
        playerListVBox.setAlignment(Pos.TOP_CENTER);

        centerHBox = new HBox();
        centerHBox.setSpacing(70);
        centerHBox.setMaxSize(50, 0);

        startButton = new SimpleButton("Start Game");
        startButton.setSize(220, 80);

        waitMessage = new Label("Wait until the host\nstarts the game");
        waitMessage.getStyleClass().add("label");

        controlsVBox = new VBox(waitMessage, startButton);

        leaveButton = new SimpleButton("Leave");
        leaveButton.setId("leave-button");
        leaveButton.setSize(80, 60);
        leaveButton.setTranslateX(-30);
        leaveButton.setTranslateY(-30);
        StackPane.setAlignment(leaveButton, Pos.BOTTOM_RIGHT);

        centerHBox.getChildren().addAll(playerListVBox, controlsVBox);
        root.getChildren().addAll(centerHBox, leaveButton, headerText);
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
     * The start game button getter.
     * @return the start game button.
     */
    public SimpleButton getStartButton() {
        return startButton;
    }

    /**
     * The leave button getter.
     * @return the leave button.
     */
    public SimpleButton getLeaveButton() {
        return leaveButton;
    }

    /**
     * Fills the five player entries with the first five names of the list.
     * @param playerNames list of new player names.
     */
    public void setPlayerNames(List<String> playerNames) {
        playerListVBox.getChildren().clear();
        List<ListEntry> entries = playerNames.stream()
                .map(ListEntry::new).collect(Collectors.toList());
        playerListVBox.getChildren().addAll(entries);
    }

    /**
     * Getter for wait message label.
     * @return the wait message.
     */
    public Label getWaitMessage() {
        return waitMessage;
    }
}
