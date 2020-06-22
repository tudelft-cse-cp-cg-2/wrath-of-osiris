package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;
import nl.tudelft.context.cg2.client.view.elements.etc.ListEntry;

import java.net.URISyntaxException;
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
    private Label tipMessage;
    private SimpleButton startButton;
    private SimpleButton leaveButton;
    private SimpleButton guideButton;

    private StackPane popup;
    private StackPane popupPane;
    private Text popupText;

    private ImageView cameraView;

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
        try {
            this.getStylesheets().add(LobbyScene.class.getClassLoader()
                    .getResource("css/createGame.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        headerText = new Text("Wrath of Osiris");
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

        guideButton = new SimpleButton("Guide");
        guideButton.setId("leave-button");
        guideButton.setSize(80, 60);
        guideButton.setTranslateX(-30);
        guideButton.setTranslateY(-100);
        StackPane.setAlignment(guideButton, Pos.BOTTOM_RIGHT);

        tipMessage = new Label("Tip: Read the guide to the right before playing!");
        tipMessage.getStyleClass().add("label");
        tipMessage.setTranslateY(-50);
        StackPane.setAlignment(tipMessage, Pos.BOTTOM_CENTER);

        popup = drawPopup();
        popupPane = drawPopupPane();
        popupText = drawPopupText();
        popupText.setTranslateX(40);
        popupText.setTranslateY(20);
        ScrollPane scrollPane = new ScrollPane(popupText);
        scrollPane.setId("scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        popupPane.getChildren().add(scrollPane);
        popup.getChildren().add(popupPane);

        // Create camera preview
        this.cameraView = new ImageView(ImageCache.IMAGES[8]);
        cameraView.setId("camera-view");
        cameraView.setFitWidth(200);
        cameraView.setFitHeight(130);
        cameraView.setTranslateX(-30);
        cameraView.setTranslateY(30);
        cameraView.setScaleX(-1D);
        StackPane.setAlignment(cameraView, Pos.TOP_RIGHT);

        centerHBox.getChildren().addAll(playerListVBox, controlsVBox);
        root.getChildren().addAll(centerHBox, guideButton, leaveButton, headerText, popup,
                tipMessage, cameraView);
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

    /**
     * Shows the message popup.
     * @param message the message to set on the popup.
     */
    public void showPopup(String message) {
        popupText.setText(message);
        popup.setVisible(true);
    }

    /**
     * Closes the message popup.
     */
    public void closePopup() {
        popup.setVisible(false);
    }

    /**
     * Gets the message popup.
     * @return the message popup.
     */
    public StackPane getPopup() {
        return popup;
    }

    /**
     * Getter for the Guide button.
     * @return the Guide button.
     */
    public SimpleButton getGuideButton() {
        return guideButton;
    }

    /**
     * Gets the camera view.
     * @return the camera view.
     */
    public ImageView getCameraView() {
        return cameraView;
    }
}
