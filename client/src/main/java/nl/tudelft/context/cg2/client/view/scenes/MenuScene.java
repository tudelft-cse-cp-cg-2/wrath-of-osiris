package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

public class MenuScene extends BaseScene {

    private Text exampleText;
    private HBox exampleHBox;

    private SimpleButton exampleButtonOne;
    private SimpleButton exampleButtonTwo;
    private SimpleButton exampleButtonThree;

    /**
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public MenuScene(Window window, Pane root) {
        super(window, root);
    }

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

    @Override
    public void animate() {

    }

    @Override
    public void onResized() {

    }

    @Override
    public void onShown() {

    }

    public SimpleButton getExampleButtonOne() {
        return exampleButtonOne;
    }

    public SimpleButton getExampleButtonTwo() {
        return exampleButtonTwo;
    }

    public SimpleButton getExampleButtonThree() {
        return exampleButtonThree;
    }
}
