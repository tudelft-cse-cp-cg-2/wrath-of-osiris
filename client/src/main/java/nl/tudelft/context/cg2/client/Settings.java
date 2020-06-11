package nl.tudelft.context.cg2.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Contains the local client settings.
 * Convenience class for loading and saving user settings.
 */
@SuppressFBWarnings(value = "OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE",
        justification = "Several try-catch blocks not required.")
public class Settings {

    public static final boolean LOCALHOST = true;

    private static int cameraIndex = 0;

    /**
     * Saves the user settings to a properties file.
     */
    public static void save() {
        File file = new File("settings.properties");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            Properties properties = new Properties();

            properties.setProperty("hardware.camera", "" + cameraIndex);
            properties.store(stream, null);
            stream.close();
            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            System.out.println("Settings saving failed.");
        }
    }

    /**
     * Loads the user settings from a properties file.
     */
    public static void load() {
        File file = new File("settings.properties");
        FileInputStream stream;

        if (file.exists()) {
            try {
                stream = new FileInputStream(file);
                Properties properties = new Properties();
                properties.load(stream);

                cameraIndex = Integer.parseInt(properties.getProperty("hardware.camera"));
                stream.close();
                System.out.println("Settings loaded successfully.");
            } catch (IOException ioe) {
                System.out.println("Settings loading failed.");
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else {
            System.out.println("Settings file not found.");
        }
    }

    /**
     * Gets the camera index.
     * @return the camera index.
     */
    public static int getCameraIndex() {
        return cameraIndex;
    }

    /**
     * Sets the camera index.
     * @param idx the camera index.
     */
    public static void setCameraIndex(int idx) {
        cameraIndex = idx;
    }
}
