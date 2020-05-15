package nl.tudelft.context.cg2.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

import static org.opencv.core.Core.flip;

/**
 * JavaFX App.
 */
@SuppressFBWarnings(value = "URF_UNREAD_FIELD",
        justification = "'controller' will be used very soon.")
public class App extends Application {
    private VideoCapture videoCapture;
    private WritableImage writableImage;
    private boolean skip = false;

    /**
     * Launches the javafx application.
     *
     * @param stage the window displayed to the user.
     */
    @Override
    public void start(Stage stage) {
        // Load in open cv library, must be done manually
        nu.pattern.OpenCV.loadLocally();

        // Init video capture
        videoCapture = new VideoCapture();
        videoCapture.open(0);

        PoseDetector pd = new PoseDetector();

        // Schedule an interval to capture a frame, process it and display it
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            WritableImage writableImage = this.captureAndProcessSnapshot(pd);

            ImageView imageView = new ImageView(writableImage);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(900);

            Group root = new Group(imageView);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        stage.setTitle("Capture image");
        stage.show();
    }

    /**
     * Captures and processes a snapshot of webcam feed.
     * @param pd - PoseDetector object
     * @return an image with the player pose marked
     */
    public WritableImage captureAndProcessSnapshot(PoseDetector pd) {
        WritableImage writableImage = null;

        Mat matrix = new Mat();
        videoCapture.read(matrix);

        if (skip) {
            skip = false;
            return this.writableImage;
        }

        flip(matrix, matrix, +1);
        if (videoCapture.isOpened()) {
            BufferedImage image = pd.generatePoseRegions(matrix);
            writableImage = SwingFXUtils.toFXImage(image, null);
        }

        skip = true;
        this.writableImage = writableImage;

        return writableImage;
    }


    /**
     * Ran as the very last method when the application is shut down.
     */
    @Override
    public void stop() {

    }

    /**
     * The main method.
     *
     * @param args arguments passed to the main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}