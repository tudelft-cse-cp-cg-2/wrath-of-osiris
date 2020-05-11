package nl.tudelft.context.cg2.client.view.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.buttons.SimpleButton;

/**
 * The create game scene.
 * Features the main menu UI as shown to the user.
 */
public class CreateGameScene extends BaseScene {

    private Text headerText;
    private VBox centerVBox;


    private TextField name;
    private TextField password;
    private SimpleButton createGameButton;
    private SimpleButton leaveButton;

    /**
     * The menu scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     */
    public CreateGameScene(Window window, Pane root) {
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

        centerVBox = new VBox();
        centerVBox.setSpacing(30);
        centerVBox.setMaxSize(50, 0);

        Label nameLabel = new Label();
        nameLabel.setLabelFor(name);
        nameLabel.setText("Lobby name:");
        nameLabel.getStyleClass().add("label");
        name = new TextField();
        name.setPromptText("Lobby name");
        name.setId("text-box1");
        name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Label passwordLabel = new Label();
        passwordLabel.setLabelFor(password);
        passwordLabel.setText("Password:");
        passwordLabel.getStyleClass().add("label");
        password = new TextField();
        password.setPromptText("Password (optional)");
        password.setId("text-box2");
        password.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        createGameButton = new SimpleButton("Create Game");
        createGameButton.setSize(220, 80);

        leaveButton = new SimpleButton("Back");
        leaveButton.setId("leave-button");
        leaveButton.setSize(80, 60);
        leaveButton.setTranslateX(-30);
        leaveButton.setTranslateY(-30);
        StackPane.setAlignment(leaveButton, Pos.BOTTOM_RIGHT);

        centerVBox.getChildren().addAll(nameLabel, name, passwordLabel, password, createGameButton);
        root.getChildren().addAll(centerVBox, leaveButton, headerText);
    }

    /**
     * Animates the scene.
     */
    @Override
    public void animate() {

    }

    /**
     * Event thrown when the window is resized.
     */
    @Override
    public void onResized() {

    }

    /**
     * Event thrown when the scene is shown in the window.
     */
    @Override
    public void onShown() {

    }

    /**
     * The create game getter.
     * @return the create game button.
     */
    public SimpleButton getCreateGameButton() {
        return createGameButton;
    }

    /**
     * The leave button getter.
     * @return the leave button.
     */
    public SimpleButton getLeaveButton() {
        return leaveButton;
    }


    /**
     * The lobby name text field getter.
     * @return the lobby name text field.
     */
    public TextField getName() {
        return name;
    }

    /**
     * The password text field getter.
     * @return the password text field.
     */
    public TextField getPassword() {
        return password;
    }
}
