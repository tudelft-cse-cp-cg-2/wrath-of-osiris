package nl.tudelft.context.cg2.client.model.files;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

public class ImageCache {

    public static final Image[] IMAGES = new Image[1000];

    public static void loadImages() {
        File folder = new File(FilePaths.IMAGES_FOLDER_PATH);

        try {
            if (folder.exists()) {
                for (int i = 0; i < Objects.requireNonNull(folder.listFiles(File::isFile)).length; i++) {
                    IMAGES[i] = new Image("/images/" + i + ".png");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
