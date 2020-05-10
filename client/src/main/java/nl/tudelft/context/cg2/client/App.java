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
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 * JavaFX App.
 */
@SuppressFBWarnings(value = "URF_UNREAD_FIELD",
        justification = "'controller' will be used very soon.")
public class App extends Application {
    VideoCapture videoCapture;
    CascadeClassifier classifier;

    /**
     * Launches the javafx application.
     *
     * @param stage the window displayed to the user.
     */
    @Override
    public void start(Stage stage) {
        nu.pattern.OpenCV.loadLocally();

        // Init video capture
        videoCapture = new VideoCapture();
        videoCapture.open(0);

        // Select a classifier
//        classifier = new CascadeClassifier("/home/asitaram/ContextProject/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_fullbody.xml");
        classifier = new CascadeClassifier("/home/asitaram/ContextProject/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");

        // Schedule an interval to capture a frame, process it and display it
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            WritableImage writableImage = this.captureAndProcessSnapshot();

            ImageView imageView = new ImageView(writableImage);
            imageView.setPreserveRatio(true);

            Group root = new Group(imageView);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        stage.setTitle("Capture image");
        stage.show();
    }

    public WritableImage captureAndProcessSnapshot() {
        WritableImage writableImage = null;

        Mat matrix = new Mat();
        videoCapture.read(matrix);

        if (videoCapture.isOpened()) {
            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

            MatOfRect faceDetections = new MatOfRect();
            classifier.detectMultiScale(matrix, faceDetections);

            // Drawing boxes
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(
                        matrix,                                   //where to draw the box
                        new Point(rect.x, rect.y),                            //bottom left
                        new Point(rect.x + rect.width, rect.y + rect.height), //top right
                        new Scalar(0, 0, 255)                                 //RGB colour
                );
            }

            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            matrix.get(0, 0, data);
            writableImage = SwingFXUtils.toFXImage(image, null);
        }

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