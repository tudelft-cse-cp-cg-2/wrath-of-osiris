package nl.tudelft.context.cg2.client;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class PoseDetector {
    final private CascadeClassifier classifier = new CascadeClassifier("/home/asitaram/ContextProject/main-repository/client/src/main/java/nl/tudelft/context/cg2/client/haarcascade_frontalface_default.xml");
    final private int red = new Color(255, 0, 0).getRGB();
    final private int green = new Color(0, 255, 0).getRGB();
    final private int blue = new Color(0, 0, 255).getRGB();

    final private Pose pose = new Pose(0, 0, 0, 0);
    private BufferedImage baseImage;

    public BufferedImage generatePoseRegions(Mat matrix) {
        List<PoseRegion> poseRegions = new ArrayList<>();
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(matrix, faceDetections);
        BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

//        drawPoseRegions(faceDetections, matrix);
        // TODO: actually fill poseRegions in above function
        /// _______________________________
        poseRegions.add(new PoseRegion(0, 0, 225, 150, 0, 0));
        poseRegions.add(new PoseRegion(0, 150, 225, 300, 0, 1));
        poseRegions.add(new PoseRegion(0, 300, 225, 450, 0, 2));
        for (PoseRegion poseRegion : poseRegions) {
            Imgproc.rectangle(matrix, poseRegion.getTopLeft(), poseRegion.getBottomRight(), new Scalar(255, 0, 0), 3, 0, 0);
        }
        /// _______________________________

        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matrix.get(0, 0, data);

        if (baseImage == null) {
            baseImage = image;
        } else {
            image = findLimbLocations(poseRegions, image);
        }

        return image;
    }

    private void drawPoseRegions(MatOfRect faceDetections, Mat matrix) {
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    matrix,                                   //where to draw the box
                    new Point(rect.x, rect.y),                            //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(0, 255, 0)                                 //RGB colour
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x + rect.width, rect.y - 3 * rect.height),
                    new Point(rect.x + 3 * rect.width, rect.y + rect.height), new Scalar(0, 0, 255)
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x + 1.5 * rect.width, rect.y + rect.height + 10),
                    new Point(rect.x + 5 * rect.width, rect.y + 3 * rect.height), new Scalar(0, 0, 255)
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x + rect.width, rect.y + rect.height * 2),
                    new Point(rect.x + 3 * rect.width, rect.y + 5 * rect.height), new Scalar(0, 0, 255)
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x, rect.y - 3 * rect.height),
                    new Point(rect.x - 2 * rect.width, rect.y + rect.height), new Scalar(0, 0, 255)
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x - 0.5 * rect.width, rect.y + rect.height + 10),
                    new Point(rect.x - 4 * rect.width, rect.y + 3 * rect.height), new Scalar(0, 0, 255)
            );
            Imgproc.rectangle(
                    matrix, new Point(rect.x, rect.y + rect.height * 2),
                    new Point(rect.x - 2 * rect.width, rect.y + 5 * rect.height), new Scalar(0, 0, 255)
            );
        }
    }


    public BufferedImage findLimbLocations(List<PoseRegion> poseRegions, BufferedImage image) {
        this.pose.resetCounters();
        BufferedImage bufferedImage = blendAndCompareImages(poseRegions, image);
        this.pose.updatePose();
        System.out.println(this.pose.toString());
        return bufferedImage;
    }

    BufferedImage blendAndCompareImages(List<PoseRegion> poseRegions, BufferedImage img) {
        int epsilon = 750000;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgbValue = (baseImage.getRGB(x, y) + img.getRGB(x, y)) / 2;
                baseImage.setRGB(x, y, rgbValue);
                int rgbValueImg = img.getRGB(x, y);
                // TODO: exclude the lines we draw ourselves
                if (Math.abs(rgbValue - rgbValueImg) > epsilon) {
                    img.setRGB(x, y, this.green);
                    for (PoseRegion poseRegion : poseRegions) {
                        if (poseRegion.inRange(x, y)) {
                            this.pose.incrementCounter(poseRegion.getLimb(), poseRegion.getOption());
                        }
                    }
                }
            }
        }
        return img;
    }


}
