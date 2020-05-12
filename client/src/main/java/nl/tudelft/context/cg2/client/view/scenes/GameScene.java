package nl.tudelft.context.cg2.client.view.scenes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import nl.tudelft.context.cg2.client.model.files.ImageCache;
import nl.tudelft.context.cg2.client.model.world.World;
import nl.tudelft.context.cg2.client.view.BaseScene;
import nl.tudelft.context.cg2.client.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The menu scene.
 * Features the main menu UI as shown to the user.
 */
public class GameScene extends BaseScene {

    private final World world;

    private ArrayList<Canvas> canvasses;
    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Canvas lightCanvas;
    private Canvas shadowLayer;

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
        //Create canvasses
        this.mainCanvas = new Canvas();
        this.backgroundCanvas = new Canvas();
        this.lightCanvas = new Canvas();
        this.shadowLayer = new Canvas();

        //Keep track of all the canvasses
        this.canvasses = new ArrayList<>(Arrays.asList(backgroundCanvas, lightCanvas, shadowLayer, mainCanvas));

        //Bind canvasses to the stage size
        for (Canvas canvas : canvasses) {
            canvas.widthProperty().bind(window.sceneWidthProperty());
            canvas.heightProperty().bind(window.sceneHeightProperty());
        }

        // Set the blend modes of the canvasses
        lightCanvas.setBlendMode(BlendMode.SCREEN);
        shadowLayer.setBlendMode(BlendMode.MULTIPLY);

        // Add the canvasses to the root
        root.getChildren().addAll(canvasses);
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
     * Pre processes all graphics before drawing the game elements.
     * Should always be called at the start of a drawing cycle.
     */
    public void preprocess() {
        // Clear the main canvas.
        GraphicsContext mainGC = getMainGraphicsContext();
        mainGC.clearRect(0, 0, getWidth(), getHeight());

        // Fill the background will black
        GraphicsContext backgroundGC = getBackgroundGraphicsContext();
        backgroundGC.setFill(Color.BLACK);
        backgroundGC.fillRect(0, 0, getWidth(), getHeight());

        // Clear the lights
        GraphicsContext lightGC = getLightGraphicsContext();
        lightGC.setGlobalBlendMode(BlendMode.SRC_OVER);
        lightGC.setFill(Color.BLACK);
        lightGC.fillRect(0, 0, getWidth(), getHeight());
        lightGC.setGlobalBlendMode(BlendMode.SCREEN);

        // Clear the shadows
        GraphicsContext shadowGC = getShadowGraphicsContext();
        shadowGC.setGlobalBlendMode(BlendMode.SRC_OVER);
        shadowGC.setFill(Color.WHITE);
        shadowGC.fillRect(0, 0, getWidth(), getHeight());
        shadowGC.setGlobalBlendMode(BlendMode.MULTIPLY);
    }

    /**
     * Redraws all the game element graphics on the scene canvasses.
     */
    private void process() {
        //Calculate draw variables
        double width = window.sceneWidthProperty().getValue();
        double height = window.sceneHeightProperty().getValue();
        double widthRatio = width / World.WIDTH;
        double heightRatio = height / World.HEIGHT;

        //Draws the background objects.
        GraphicsContext backgroundGC = getBackgroundGraphicsContext();
        backgroundGC.drawImage(ImageCache.IMAGES[1], 0, 0, width * 0.495D, height);
        backgroundGC.drawImage(ImageCache.IMAGES[2], width * 0.505D, 0, width * 0.495D, height);

        //Draws the world objects on the screen.
        world.getEntities().forEach(e -> {
            Image image = e.getTexture();
            double w = e.getSize().x * widthRatio * e.getDepthScalar();
            double h = e.getSize().y * heightRatio * e.getDepthScalar();
            double x = (e.getPosition().x) * widthRatio + ((w / e.getDepthScalar() - w) * 0.5D);
            double y = e.getPosition().x * heightRatio + ((h / e.getDepthScalar() - h) * 0.5D);

            getMainGraphicsContext().drawImage(image, x, y, w, h);

            if(e.getDepthScalar() > 0D) {
                double darkScalar = 0.6D;
                getMainGraphicsContext().setFill(new Color(0, 0, 0,darkScalar - e.getDepthScalar() * darkScalar));
                getMainGraphicsContext().fillRect(x, y, w, h);
            }
        });
    }

    /**
     * Post processes all graphics after drawing the game entities.
     * Should always be called at the end of a drawing cycle.
     */
    public void postprocess() {

    }

    /**
     * Saves the state of all GraphicsContexts from the layers. This save is pushed onto a stack and does not overwrite
     * previous saves.
     */
    public void save() {
        for (GraphicsContext graphicsContext : getAllGraphicsContexts()) {
            graphicsContext.save();
        }
    }

    /**
     * Restores the state of all GraphicsContexts from the layers. This takes the state from the previous save and
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
        return Arrays.asList(getMainGraphicsContext(), getBackgroundGraphicsContext(),
                getLightGraphicsContext(), getShadowGraphicsContext());
    }

    /**
     * Gets the GraphicsContext of the main canvas. This canvas should be used for drawing normal sprites
     * (no effects of any sort) meant to be between the back- and foreground.
     * @return The GraphicsContext of the main canvas
     */
    public GraphicsContext getMainGraphicsContext() {
        return mainCanvas.getGraphicsContext2D();
    }

    /**
     * Gets the GraphicsContext of the background canvas. This canvas should be used for drawing the background.
     * The background should not be transparent anywhere.
     * @return The GraphicsContext of the background canvas
     */
    public GraphicsContext getBackgroundGraphicsContext() {
        return backgroundCanvas.getGraphicsContext2D();
    }

    /**
     * Gets the GraphicsContext of the light canvas. This canvas should be used for drawing lighting.
     * @return The GraphicsContext of the light canvas
     */
    public GraphicsContext getLightGraphicsContext() {
        return lightCanvas.getGraphicsContext2D();
    }

    /**
     * Gets the GraphicsContext of the shadow canvas. This canvas should be used for drawing shadows that
     * darkens the color from the background, main and light layers.
     * @return The GraphicsContext of the shadow canvas
     */
    public GraphicsContext getShadowGraphicsContext() {
        return shadowLayer.getGraphicsContext2D();
    }
}
