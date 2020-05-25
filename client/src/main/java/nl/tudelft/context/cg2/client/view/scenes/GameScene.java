package nl.tudelft.context.cg2.client.view.scenes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;
import nl.tudelft.context.cg2.client.view.elements.Heart;

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
    private Canvas lightCanvas;

    private VBox lifePane;
    private ArrayList<Heart> hearts;

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
        this.getStylesheets().add("/css/game.css");

        //Create game canvasses
        this.backgroundCanvas = new Canvas();
        this.objectCanvas = new Canvas();
        this.lightCanvas = new Canvas();

        //Keep track of all the canvasses
        this.canvasses = new ArrayList<>(Arrays.asList(
                backgroundCanvas, objectCanvas, lightCanvas
        ));

        //Bind canvasses to the stage size
        for (Canvas canvas : canvasses) {
            canvas.widthProperty().bind(window.sceneWidthProperty());
            canvas.heightProperty().bind(window.sceneHeightProperty());
        }

        // Set the blend modes of the canvasses
        lightCanvas.setBlendMode(BlendMode.SCREEN);

        // Add UI elements.
        this.lifePane = new VBox();
        lifePane.layoutXProperty().bind(window.sceneHeightProperty().multiply(0.015D));
        lifePane.layoutYProperty().bind(window.sceneHeightProperty().multiply(0.015D));
        lifePane.spacingProperty().bind(window.sceneHeightProperty().multiply(0.01D));

        this.hearts = new ArrayList<>(Arrays.asList(
                new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7]),
                new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7]),
                new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7]),
                new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7]),
                new Heart(ImageCache.IMAGES[6], ImageCache.IMAGES[7])
        ));

        hearts.forEach(heart -> {
            heart.setPreserveRatio(true);
            heart.fitHeightProperty().bind(window.sceneHeightProperty().multiply(0.07D));
        });

        hearts.get(4).deactivate();
        hearts.get(3).deactivate();

        this.cameraView = new ImageView(ImageCache.IMAGES[8]);
        cameraView.setId("camera-view");
        cameraView.fitWidthProperty().bind(window.sceneHeightProperty().multiply(0.35D));
        cameraView.fitHeightProperty().bind(cameraView.fitWidthProperty().multiply(0.667D));
        cameraView.layoutXProperty().bind(window.sceneWidthProperty()
                .subtract(cameraView.fitWidthProperty().multiply(1.1D)));
        cameraView.layoutYProperty().bind(window.sceneHeightProperty().multiply(0.02D));

        // Create scene node family tree.
        lifePane.getChildren().addAll(hearts);
        root.getChildren().addAll(canvasses);
        root.getChildren().addAll(lifePane, cameraView);
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

        // Clear the lights
        GraphicsContext lightGC = getLightGraphicsContext();
        lightGC.setGlobalBlendMode(BlendMode.SRC_OVER);
        lightGC.setFill(Color.BLACK);
        lightGC.fillRect(0, 0, getWidth(), getHeight());
        lightGC.setGlobalBlendMode(BlendMode.SCREEN);
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
        world.getEntities().forEach(e -> {
            Image image = e.getTexture();
            double w = e.getSize().x * widthRatio * e.getDepthScalar();
            double h = e.getSize().y * heightRatio * e.getDepthScalar();
            double x = (e.getPosition().x) * widthRatio + ((width - w) * 0.5D);
            double y = offY + (e.getPosition().x * heightRatio + ((height - h) * 0.5D));

            getObjectGraphicsContext().drawImage(image, x, y, w, h);

            if (e.getDepthScalar() > 0D) {
                double darkScalar = 0.6D;
                getObjectGraphicsContext().setFill(new Color(0, 0, 0,
                        darkScalar - e.getDepthScalar() * darkScalar));
                getObjectGraphicsContext().fillRect(x, y, w, h);
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
                getBackgroundGraphicsContext(),
                getLightGraphicsContext());
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
     * Gets the GraphicsContext of the light canvas.
     * This canvas should be used for drawing lighting.
     * @return The GraphicsContext of the light canvas
     */
    public GraphicsContext getLightGraphicsContext() {
        return lightCanvas.getGraphicsContext2D();
    }
}
