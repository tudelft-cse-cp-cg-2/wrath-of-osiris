package nl.tudelft.context.cg2.client;

import com.github.sarxos.webcam.Webcam;
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

    private static String serverIp = "131.180.178.142";
    private static int serverPort = 43594;
    private static String webcamName = Webcam.getDefault().getName();
    private static boolean debugMode = false;

    /**
     * Saves the user settings to a properties file.
     */
    public static void save() {
        File file = new File("settings.properties");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            Properties properties = new Properties();

            properties.setProperty("server.ip", "" + serverIp);
            properties.setProperty("server.port", "" + serverPort);
            properties.setProperty("hardware.camera", "" + webcamName);
            properties.setProperty("debug.mode", "" + debugMode);

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

                if (properties.getProperty("server.ip") != null) {
                    serverIp = properties.getProperty("server.ip");
                }

                if (properties.getProperty("server.port") != null) {
                    serverPort = Integer.parseInt(properties.getProperty("server.port"));
                }

                if (properties.getProperty("hardware.camera") != null) {
                    webcamName = properties.getProperty("hardware.camera");
                }

                if (properties.getProperty("debug.mode") != null) {
                    debugMode = Boolean.parseBoolean(properties.getProperty("debug.mode"));
                }

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
     * Prints a debug statement if debug mode is active.
     * @param string the string to print.
     */
    public static void debugMessage(String string) {
        if (debugMode) {
            System.out.println(string);
        }
    }

    /**
     * Gets the camera.
     * @return the camera.
     */
    public static String getWebcamName() {
        return webcamName;
    }

    /**
     * Sets the camera.
     * @param webcamName the camera name.
     */
    public static void setWebcamName(String webcamName) {
        Settings.webcamName = webcamName;
    }

    /**
     * Gets the server ip setting.
     * @return the server ip.
     */
    public static String getServerIp() {
        return serverIp;
    }

    /**
     * Gets the server port setting.
     * @return the server port.
     */
    public static int getServerPort() {
        return serverPort;
    }
}
