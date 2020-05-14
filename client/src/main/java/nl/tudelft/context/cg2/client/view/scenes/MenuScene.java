package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

/**
 * The menu scene.
 * Features the main menu UI as shown to the user.
 */
public class MenuScene extends BaseScene {

    private Text exampleText;
    private HBox exampleHBox;

    private SimpleButton exampleButtonOne;
    private SimpleButton exampleButtonTwo;
    private SimpleButton exampleButtonThree;

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
        this.getStylesheets().add("/css/menu.css");

        exampleText = new Text("Hello world!");
        exampleText.setId("example-text");
        exampleText.setTranslateY(20);
        StackPane.setAlignment(exampleText, Pos.TOP_CENTER);

        exampleHBox = new HBox();
        exampleHBox.setSpacing(50);
        exampleHBox.setMaxSize(0, 50);

        exampleButtonOne = new SimpleButton("Start");
        exampleButtonOne.setSize(150, 50);

        exampleButtonTwo = new SimpleButton("Stop");
        exampleButtonTwo.setSize(150, 50);

        exampleButtonThree = new SimpleButton("Quit");
        exampleButtonThree.setId("example-button-three");
        exampleButtonThree.setSize(60, 40);
        exampleButtonThree.setTranslateX(-30);
        exampleButtonThree.setTranslateY(-30);
        StackPane.setAlignment(exampleButtonThree, Pos.BOTTOM_RIGHT);

        exampleHBox.getChildren().addAll(exampleButtonOne, exampleButtonTwo);
        root.getChildren().addAll(exampleHBox, exampleButtonThree, exampleText);
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
    public SimpleButton getExampleButtonOne() {
        return exampleButtonOne;
    }

    /**
     * The example button two getter.
     * @return the example button two.
     */
    public SimpleButton getExampleButtonTwo() {
        return exampleButtonTwo;
    }

    /**
     * The example button three getter.
     * @return the example button three.
     */
    public SimpleButton getExampleButtonThree() {
        return exampleButtonThree;
    }
}
