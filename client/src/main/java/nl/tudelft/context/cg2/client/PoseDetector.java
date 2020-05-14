package nl.tudelft.context.cg2.client;

import nl.tudelft.context.cg2.client.PoseRegion;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class PoseDetector {
    private CascadeClassifier classifier = new CascadeClassifier("/home/mpm/Git/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");

    //public List<PoseRegion> generatePoseRegions(Mat matrix) {
    public BufferedImage generatePoseRegions(Mat matrix) {
        List<PoseRegion> out = new ArrayList<PoseRegion>();
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(matrix, faceDetections);
        BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

        // Drawing boxes
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x, rect.y),                            //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(0, 255, 0)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x + rect.width, rect.y - 3 * rect.height),                            //bottom left
                    new Point(rect.x + 3 * rect.width, rect.y + rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x + 1.5 * rect.width, rect.y + rect.height + 10),                            //bottom left
                    new Point(rect.x + 5 * rect.width, rect.y + 3 * rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x + rect.width, rect.y + rect.height * 2),                            //bottom left
                    new Point(rect.x + 3 * rect.width, rect.y + 5 * rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x, rect.y - 3 * rect.height),                            //bottom left
                    new Point(rect.x - 2 * rect.width, rect.y + rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x - 0.5 * rect.width, rect.y + rect.height + 10),                            //bottom left
                    new Point(rect.x - 4 * rect.width, rect.y + 3 * rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x, rect.y + rect.height * 2),                            //bottom left
                    new Point(rect.x - 2 * rect.width, rect.y + 5 * rect.height), //top right
                    new Scalar(0, 0, 255)                                 //RGB colour
            );
        }
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matrix.get(0, 0, data);

        return image;

        //return out;
    }

    public boolean findLimbLocations() {
        return false;
    }
}
