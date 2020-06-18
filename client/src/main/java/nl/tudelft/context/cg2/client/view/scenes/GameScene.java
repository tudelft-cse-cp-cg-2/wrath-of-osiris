package nl.tudelft.context.cg2.client.view.scenes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.model.world.superscripts.Superscript;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.Heart;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The menu scene.
 * Features the main menu UI as shown to the user.
 */
public class GameScene extends BaseScene {

    private static final double TOP_BAR_RATIO = 0.92D;

    private final World world;

    private ArrayList<Canvas> canvasses;
    private Canvas backgroundCanvas;
    private Canvas objectCanvas;

    private Heart heart;
    private Text heartCount;

    private ImageView cameraView;

    /**
     * The menu scene constructor.
     * @param window the window currently showing.
     * @param root   the root UI element.
     * @param world the game world that is to be drawn in this scene.
     */
    public GameScene(Window window, Pane root, World world) {
        super(window, root);
        this.world = world;
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        try {
            this.getStylesheets().add(CreateGameScene.class.getClassLoader()
                    .getResource("css/game.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Create game canvasses
        this.backgroundCanvas = new Canvas();
        this.objectCanvas = new Canvas();

        //Keep track of all the canvasses
        this.canvasses = new ArrayList<>(Arrays.asList(
                backgroundCanvas, objectCanvas
        ));

        //Bind canvasses to the stage size
        for (Canvas canvas : canvasses) {
            canvas.widthProperty().bind(window.sceneWidthProperty());
            canvas.heightProperty().bind(window.sceneHeightProperty());
        }

        // Add UI elements.
        heartCount = new Text();
        heartCount.setId("heart-count");
        heartCount.layoutXProperty().bind(window.sceneHeightProperty().multiply(0.09D));
        heartCount.layoutYProperty().bind(window.sceneHeightProperty().multiply(0.053D));
        heart = new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7]);
        heart.setPreserveRatio(true);
        heart.fitHeightProperty().addListener((obj, oldV, newV) -> heartCount
                .setFont(Font.font("Comic Sans", FontWeight.BOLD, newV.doubleValue() * 0.45D)));
        heart.fitHeightProperty().bind(window.sceneHeightProperty().multiply(0.06D));
        heart.layoutXProperty().bind(window.sceneHeightProperty().multiply(0.015D));
        heart.layoutYProperty().bind(window.sceneHeightProperty().multiply(0.015D));

        this.cameraView = new ImageView(ImageCache.IMAGES[8]);
        cameraView.setId("camera-view");
        cameraView.fitWidthProperty().bind(window.sceneHeightProperty().multiply(0.35D));
        cameraView.fitHeightProperty().bind(cameraView.fitWidthProperty().multiply(0.667D));
        cameraView.layoutXProperty().bind(window.sceneWidthProperty()
                .subtract(cameraView.fitWidthProperty().multiply(1.1D)));
        cameraView.layoutYProperty().bind(window.sceneHeightProperty().multiply(0.02D));
        cameraView.setScaleX(-1D);

        // Create scene node family tree.
        root.getChildren().addAll(canvasses);
        root.getChildren().addAll(heart, heartCount, cameraView);
    }

    /**
     * Animates the scene.
     */
    @Override
    public void animate() {
        preprocess();
        process();
        postprocess();
    }

    /**
     * Event thrown when the scene is shown in the window.
     */
    @Override
    public void onShown() {

    }

    /**
     * Clears the game scene by pre processing.
     */
    public void clear() {
        preprocess();
    }

    /**
     * Pre processes all graphics before drawing the game elements.
     * Should always be called at the start of a drawing cycle.
     */
    private void preprocess() {
        // Clear the object canvas.
        GraphicsContext objectGC = getObjectGraphicsContext();
        objectGC.clearRect(0, 0, getWidth(), getHeight());

        // Fill the background with black
        GraphicsContext backgroundGC = getBackgroundGraphicsContext();
        backgroundGC.setFill(Color.BLACK);
        backgroundGC.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Redraws all the game element graphics on the scene canvasses.
     */
    private void process() {
        //Calculate draw variables
        final double width = window.sceneWidthProperty().getValue();
        final double height = window.sceneHeightProperty().multiply(TOP_BAR_RATIO).getValue();
        final double offY = window.sceneHeightProperty().subtract(height).getValue();
        final double widthRatio = width / World.WIDTH;
        final double heightRatio = height / World.HEIGHT;

        //Draws the background objects.
        GraphicsContext backgroundGC = getBackgroundGraphicsContext();
        backgroundGC.drawImage(ImageCache.IMAGES[3], 0, offY + height * 0.5D, width, height * 0.5D);
        backgroundGC.drawImage(ImageCache.IMAGES[4], 0, offY, width, height * 0.5D);
        backgroundGC.drawImage(ImageCache.IMAGES[1], 0, offY, width * 0.495D, height);
        backgroundGC.drawImage(ImageCache.IMAGES[2], width * 0.505D, offY, width * 0.495D, height);
        backgroundGC.drawImage(ImageCache.IMAGES[5], 0, 0, width, offY);

        //Draws the world objects on the screen.
        GraphicsContext ogc = getObjectGraphicsContext();
        world.getEntities().forEach(e -> {
            Image image = e.getTexture();
            double d = e.getDepthScalar();
            double w = e.getSize().x * widthRatio * d;
            double h = e.getSize().y * heightRatio * d;
            double x = e.getPosition().x * widthRatio + (1 - d)
                    * (width * 0.5D - e.getPosition().x * widthRatio);
            double y = offY + height - h + (e.getPosition().y * heightRatio)
                    - (1 - d) * (height * 0.5D - e.getPosition().y * heightRatio);
            ogc.drawImage(image, x, y, w, h);

            if (e.getSuperscript() != null) {
                Superscript s = e.getSuperscript();
                ogc.setTextAlign(TextAlignment.CENTER);
                ogc.setFill(s.getColor());
                ogc.setFont(Font.font(s.getFamily(), s.getWeight(), s.getSize() * d));
                ogc.fillText(s.getString(), x + w / 2, y - s.getSpacing() * d);
            }
        });
    }

    /**
     * Post processes all graphics after drawing the game entities.
     * Should always be called at the end of a drawing cycle.
     */
    private void postprocess() {

    }

    /**
     * Saves the state of all GraphicsContexts from the layers.
     * This save is pushed onto a stack and does not overwrite previous saves.
     */
    public void save() {
        for (GraphicsContext graphicsContext : getAllGraphicsContexts()) {
            graphicsContext.save();
        }
    }

    /**
     * Restores the state of all GraphicsContexts from the layers.
     * This takes the state from the previous save and
     * removes that save from a stack.
     */
    public void restore() {
        for (GraphicsContext graphicsContext : getAllGraphicsContexts()) {
            graphicsContext.restore();
        }
    }

    /**
     * Gets a list of all GraphicsContexts of all canvasses.
     * @return A list of all GraphicsContexts of all canvasses.
     */
    public List<GraphicsContext> getAllGraphicsContexts() {
        return Arrays.asList(getObjectGraphicsContext(),
                getBackgroundGraphicsContext());
    }

    /**
     * Gets the GraphicsContext of the background canvas.
     * This canvas should be used for drawing the background.
     * The background should not be transparent anywhere.
     * @return The GraphicsContext of the background canvas
     */
    public GraphicsContext getBackgroundGraphicsContext() {
        return backgroundCanvas.getGraphicsContext2D();
    }

    /**
     * Gets the GraphicsContext of the object canvas.
     * This canvas should be used for drawing the object sprites.
     * @return The GraphicsContext of the object canvas
     */
    public GraphicsContext getObjectGraphicsContext() {
        return objectCanvas.getGraphicsContext2D();
    }

    /**
     * Sets the hearts to display.
     * @param amount the amount of hearts.
     */
    public void setHearts(int amount) {
        if (amount == 0) {
            heart.deactivate();
            heartCount.setText("");
        } else {
            heart.activate();
            heartCount.setText("x" + amount);
        }
    }

    /**
     * Gets the camera view.
     * @return the camera view.
     */
    public ImageView getCameraView() {
        return cameraView;
    }
}
