package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * The settings scene.
 * Features the settings UI as shown to the user.
 */
public class SettingsScene extends BaseScene {

    private ArrayList<SimpleButton> options = new ArrayList<>();
    private VBox centerVBox;
    private SimpleButton leaveButton;
    private int selectedOption = 0;

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

        if (options.size() == 0) {
            Text label = new Text("No camera has been detected");
            label.setId("sub-header-text");
            centerVBox.getChildren().addAll(label);
        } else {
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
    }

    /**
     * The leave button getter.
     * @return the leave button.
     */
    public SimpleButton getLeaveButton() {
        return leaveButton;
    }

    /**
     * The options getter.
     * @return the options.
     */

    public ArrayList<SimpleButton> getOptions() {
        return options;
    }

    /**
     * The options setter.
     * @param options the options.
     */
    public void setOptions(ArrayList<SimpleButton> options) {
        this.options = options;
    }

    /**
     * The selected option setter.
     * @param val the value.
     */
    public void setSelectedOption(int val) {
        selectedOption = val;
    }
}
