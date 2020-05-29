package nl.tudelft.context.cg2.client.model.files;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * The ImageCache class.
 * Loads and caches all the image files. used in the client.
 */
public final class ImageCache {

    @SuppressFBWarnings(value = "MS_MUTABLE_ARRAY", justification = "Array cache required here.")
    public static final Image[] IMAGES = new Image[1000];
    public static final int IMAGE_COUNT = 18;

    /**
     * Loads the client images from the image source folder.
     */
    public static void loadImages() {
        try {
            for (int i = 0; i < IMAGE_COUNT; i++) {
                InputStream stream = ImageCache.class.getClassLoader()
                        .getResourceAsStream("images/" + i + ".png");
                if (stream == null) {
                    throw new RuntimeException("Could not find resource");
                }
                IMAGES[i] = new Image(stream);
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
