package nl.tudelft.context.cg2.client.model.files;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

/**
 * The ImageCache class.
 * Loads and caches all the image files. used in the client.
 */
public final class ImageCache {

    @SuppressFBWarnings(value = "MS_MUTABLE_ARRAY", justification = "Array cache required here.")
    public static final Image[] IMAGES = new Image[1000];

    /**
     * Loads the client images from the image source folder.
     */
    public static void loadImages() {
        File folder = new File(FilePaths.IMAGES_FOLDER_PATH);
        try {
            if (folder.exists()) {
                int x = Objects.requireNonNull(folder.listFiles(File::isFile)).length;
                for (int i = 0; i < x; i++) {
                    IMAGES[i] = new Image("/images/" + i + ".png");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * The unused utility class constructor.
     */
    private ImageCache() {

    }
}
