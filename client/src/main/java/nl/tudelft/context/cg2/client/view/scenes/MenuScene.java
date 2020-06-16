package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

import java.net.URISyntaxException;

/**
 * The menu scene.
 * Features the main menu UI as shown to the user.
 */
public class MenuScene extends BaseScene {

    private Text headerText;
    private HBox centerHBox;

    private SimpleButton joinGameButton;
    private SimpleButton createGameButton;
    private SimpleButton settingsButton;
    private SimpleButton quitButton;

    private StackPane popup;
    private StackPane popupPane;
    private Text popupText;

    /**
     * The menu scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public MenuScene(Window window, Pane root) {
        super(window, root);
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        try {
            this.getStylesheets().add(MenuScene.class.getClassLoader()
                    .getResource("css/menu.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        headerText = new Text("Wrath of Osiris");
        headerText.setId("header-text");
        headerText.setTranslateY(20);
        StackPane.setAlignment(headerText, Pos.TOP_CENTER);

        centerHBox = new HBox();
        centerHBox.setSpacing(50);
        centerHBox.setMaxSize(0, 50);

        joinGameButton = new SimpleButton("Join Game");
        joinGameButton.setSize(220, 80);

        createGameButton = new SimpleButton("Create Game");
        createGameButton.setSize(220, 80);

        settingsButton = new SimpleButton("Settings");
        settingsButton.setSize(220, 80);

        quitButton = new SimpleButton("Quit");
        quitButton.setId("quit-button");
        quitButton.setSize(80, 60);
        quitButton.setTranslateX(-30);
        quitButton.setTranslateY(-30);
        StackPane.setAlignment(quitButton, Pos.BOTTOM_RIGHT);

        popup = drawPopup();
        popupPane = drawPopupPane();
        popupText = drawPopupText();

        popupPane.getChildren().add(popupText);
        popup.getChildren().add(popupPane);
        centerHBox.getChildren().addAll(joinGameButton, createGameButton, settingsButton);
        root.getChildren().addAll(centerHBox, quitButton, headerText, popup);
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
     * The example button one getter.
     * @return the example button one.
     */
    public SimpleButton getJoinGameButton() {
        return joinGameButton;
    }

    /**
     * The example button two getter.
     * @return the example button two.
     */
    public SimpleButton getCreateGameButton() {
        return createGameButton;
    }

    /**
     * The example button three getter.
     * @return the example button three.
     */
    public SimpleButton getSettingsButton() {
        return settingsButton;
    }

    /**
     * The example button three getter.
     * @return the example button three.
     */
    public SimpleButton getQuitButton() {
        return quitButton;
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
}
