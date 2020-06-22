package nl.tudelft.context.cg2.client.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * The base scene class.
 * Can be set in a window to show a scene.
 */
public abstract class BaseScene extends Scene {

    protected final Window window;
    protected final Pane root;

    private static final int MAX_PLAYER_NAME_LENGTH = 15;

    /**
     * The base scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public BaseScene(Window window, Pane root) {
        super(root);
        this.window = window;
        this.root = root;
    }

    /**
     * Shows the scene.
     */
    public void show() {
        window.showScene(this);
        onShown();
    }

    /**
     * Draws the scene.
     */
    public abstract void draw();

    /**
     * Animates the scene.
     */
    public abstract void animate();

    /**
     * Event thrown when the scene is shown in the window.
     */
    public abstract void onShown();

    /**
     * Creates the Popup needed in child classes.
     * @return a popup
     */
    protected StackPane drawPopup() {
        StackPane popup = new StackPane();
        popup.minWidthProperty().bind(window.sceneWidthProperty());
        popup.getStyleClass().add("popup");
        popup.maxWidthProperty().bind(window.sceneWidthProperty());
        popup.minHeightProperty().bind(window.sceneHeightProperty());
        popup.maxHeightProperty().bind(window.sceneHeightProperty());
        popup.setVisible(false);
        return popup;
    }

    /**
     * Creates the PopupPane needed in child classes.
     * @return a popup pane
     */
    protected StackPane drawPopupPane() {
        StackPane popupPane = new StackPane();
        popupPane.setMinSize(500, 300);
        popupPane.setMaxSize(500, 300);
        popupPane.getStyleClass().add("popup-pane");
        return popupPane;
    }

    /**
     * Creates the PopupText needed in child classes.
     * @return a popup text field
     */
    protected Text drawPopupText() {
        Text popupText = new Text();
        popupText.setWrappingWidth(400);
        popupText.setTextAlignment(TextAlignment.CENTER);
        popupText.getStyleClass().add("popup-text");
        return popupText;
    }

    /**
     * String validator for TextField objects.
     * @param oldV old value
     * @param newV new value
     * @param playerNameField field to validate
     */
    public static void validateInput(String oldV, String newV, TextField playerNameField) {
        if (newV.equals("")) {
            return;
        }
        if (newV.length() > MAX_PLAYER_NAME_LENGTH || !newV.matches("\\S+")) {
            playerNameField.setText(oldV);
        }
    }
}