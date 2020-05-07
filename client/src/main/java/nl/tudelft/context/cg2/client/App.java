package nl.tudelft.context.cg2.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
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
    /**
     * Launches the javafx application.
     *
     * @param stage the window displayed to the user.
     */
    @Override
    public void start(Stage stage) {
        nu.pattern.OpenCV.loadLocally();

        App obj = new App();
        WritableImage writableImage = obj.captureSnapshot();

        ImageView imageView = new ImageView(writableImage);
        imageView.setPreserveRatio(true);

        Group root = new Group(imageView);
        Scene scene = new Scene(root);
        stage.setTitle("Capture image");
        stage.setScene(scene);

        stage.show();
    }

    public WritableImage captureSnapshot() {
        WritableImage writableImage = null;

        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open(0);
        Mat matrix = new Mat();
        videoCapture.read(matrix);

        if (videoCapture.isOpened() && videoCapture.read(matrix)) {
            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

            CascadeClassifier cascadeClassifier = new CascadeClassifier("/home/asitaram/ContextProject/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_fullbody.xml");
//            CascadeClassifier cascadeClassifier = new CascadeClassifier("/home/asitaram/ContextProject/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");

            MatOfRect faceDetections = new MatOfRect();
            cascadeClassifier.detectMultiScale(matrix, faceDetections, 1.05, 0);
            System.out.println(String.format("Detected %s faces",
                    faceDetections.toArray().length));

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