package nl.tudelft.context.cg2.client.view.scenes;

import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;
import org.opencv.videoio.VideoCapture;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * The settings scene.
 * Features the settings UI as shown to the user.
 */
public class SettingsScene extends BaseScene {

    private ArrayList<SimpleButton> options = new ArrayList<>();
    private VBox centerVBox;
    private SimpleButton leaveButton;
    private int selectedOption = -1;

    /**
     * The menu scene constructor.
     * @param window the window currently showing.
     * @param root the root UI element.
     */
    public SettingsScene(Window window, Pane root) {
        super(window, root);
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        try {
            this.getStylesheets().add(SettingsScene.class.getClassLoader()
                    .getResource("css/createGame.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Text headerText = new Text("Settings");
        headerText.setId("header-text");
        headerText.setTranslateY(20);
        StackPane.setAlignment(headerText, Pos.TOP_CENTER);

        centerVBox = new VBox();
        centerVBox.setSpacing(25);
        centerVBox.setMaxSize(50, 0);

        leaveButton = new SimpleButton("Back");
        leaveButton.setId("leave-button");
        leaveButton.setSize(80, 60);
        leaveButton.setTranslateX(-30);
        leaveButton.setTranslateY(-30);
        StackPane.setAlignment(leaveButton, Pos.BOTTOM_RIGHT);
        root.getChildren().addAll(centerVBox, leaveButton, headerText);
    }

    /**
     * updates the scene.
     */
    public void reDraw() {
        centerVBox.getChildren().clear();
        if (selectedOption < 0 && options.size() == 1) {
            selectedOption = getIndexFromButton(options.get(0));
        }

        if (options.size() == 0) {
            Text label = new Text("No camera has been detected");
            label.setId("sub-header-text");
            centerVBox.getChildren().addAll(label);
        } else if (selectedOption >= 0) {
            Text label = new Text("Camera " + selectedOption + " has been selected");
            label.setId("sub-header-text");
            centerVBox.getChildren().addAll(label);
        }
        for (SimpleButton button: options) {
            centerVBox.getChildren().addAll(button);
        }
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
        nu.pattern.OpenCV.loadLocally();
        options = new ArrayList<>();
        VideoCapture videoCapture = new VideoCapture();
        for (int i = 0; i < 10; i++) {
            videoCapture.open(i);
            if (videoCapture.isOpened()) {
                SimpleButton button = new SimpleButton("Camera index: " + i);
                button.setSize(220, 50);
                button.setOnMouseClicked(this::selectCamera);
                options.add(button);
            }
            videoCapture.release();
        }
        reDraw();
    }

    /**
     * The leave button getter.
     * @return the leave button.
     */
    public SimpleButton getLeaveButton() {
        return leaveButton;
    }

    /**
     * get the selected option.
     * @return the selected option.
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    /**
     * Select a camera.
     * @param event the event.
     */
    private void selectCamera(MouseEvent event) {
        SimpleButton btn = (SimpleButton) event.getSource();
        selectedOption = getIndexFromButton(btn);
        reDraw();
    }

    /**
     * Get the index from a button.
     * @param simpleButton the simpleButton.
     * @return the index.
     */
    private int getIndexFromButton(SimpleButton simpleButton) {
        String[] parts = simpleButton.getText().split(" ");
        return parseInt(parts[parts.length - 1]);
    }
}
