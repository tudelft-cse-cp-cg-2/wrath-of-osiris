package nl.tudelft.context.cg2.client.controller.io.textures;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import nl.tudelft.context.cg2.client.controller.io.posedetection.Pose;
import nl.tudelft.context.cg2.client.model.files.ImageCache;

/**
 * A factory class that generates texture from sprite sheets.
 */
@SuppressWarnings(value = "HideUtilityClassConstructor")
public class TextureFactory {

    public static final int POSE_TEX_FRAME_WIDTH = 134;
    public static final int POSE_TEX_FRAME_HEIGHT = 178;
    public static final int POSE_TEX_ARM_HEIGHT = 104;
    public static final int POSE_TEX_LEG_HEIGHT = 74;

    /**
     * Generates a default avatar texture with a given color.
     * @param color the color of the avatar texture.
     * @return the default avatar texture.
     */
    public static Image defaultAvatarTexture(Color color) {
        return avatarTexture(color, 0, 0, 0, 0);
    }

    /**
     * Generates an avatar texture for the pose.
     * @param pose the pose to generate the avatar texture for.
     * @param color the color of the avatar texture.
     * @return the composed avatar texture.
     */
    public static Image avatarTexture(Pose pose, Color color) {
        Image texture = null;
        try {
            texture = (TextureFactory.avatarTexture(color,
                    pose.getLeftArm().indexOf(),
                    pose.getRightArm().indexOf(),
                    pose.getLeftLeg().indexOf(),
                    pose.getRightLeg().indexOf()));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid pose arguments passed to texture generator.");
        }
        return texture;
    }

    /**
     * Generates a player avatar texture for the frame input variables.
     * These pose input variables correspond to pose arm and leg ids.
     * @param color the color of the avatar texture.
     * @param tlFrame the top left frame of the texture.
     * @param trFrame the top right frame of the texture.
     * @param lbFrame the bottom left frame of the texture.
     * @param rbFrame the bottom right frame of the texture.
     * @return an avatar texture generated from the avatar sprite sheets.
     */
    public static Image avatarTexture(Color color, int tlFrame, int trFrame,
                                      int lbFrame, int rbFrame) {
        WritableImage tex = generatePoseTexture(9, tlFrame, 10, trFrame, 11, lbFrame, 12, rbFrame);
        applyTextureColor(tex, color);
        return tex;
    }

    /**
     * Generates a hole texture for the pose.
     * @param pose the pose to generate the hole texture for.
     * @return the composed hole texture.
     */
    public static Image holeTexture(Pose pose) {
        Image texture = null;
        try {
            texture = TextureFactory.holeTexture(pose.getLeftArm().indexOf(),
                    pose.getRightArm().indexOf(),
                    pose.getLeftLeg().indexOf(),
                    pose.getRightLeg().indexOf());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid pose arguments passed to texture generator.");
        }
        return texture;
    }

    /**
     * Generates a hole texture for the frame input variables.
     * These pose input variables correspond to pose arm and leg ids.
     * @param tlFrame the top left frame of the texture.
     * @param trFrame the top right frame of the texture.
     * @param lbFrame the bottom left frame of the texture.
     * @param rbFrame the bottom right frame of the texture.
     * @return a hole texture generated from the hole sprite sheets.
     */
    private static Image holeTexture(int tlFrame, int trFrame, int lbFrame, int rbFrame) {
        return generatePoseTexture(13, tlFrame, 14, trFrame, 15, lbFrame, 16, rbFrame);
    }

    /**
     * Generates a pose texture based on the sprite sheet variables.
     * @param tlSprite the top left sprite sprite sheet.
     * @param tlFrame the top left frame number.
     * @param trSprite the top right sprite sprite sheet.
     * @param trFrame the top right frame number.
     * @param lbSprite the bottom left sprite sprite sheet.
     * @param lbFrame the bottom left frame number.
     * @param rbSprite the bottom right sprite sprite sheet.
     * @param rbFrame the bottom right frame number.
     * @return the composed pose texture.
     */
    @SuppressWarnings(value = "ParameterNumber")
    private static WritableImage generatePoseTexture(int tlSprite, int tlFrame, int trSprite,
                 int trFrame, int lbSprite, int lbFrame, int rbSprite, int rbFrame) {
        WritableImage tex = new WritableImage(POSE_TEX_FRAME_WIDTH, POSE_TEX_FRAME_HEIGHT);
        tex.getPixelWriter().setPixels(0, 0, POSE_TEX_FRAME_WIDTH / 2, POSE_TEX_ARM_HEIGHT,
                ImageCache.IMAGES[tlSprite].getPixelReader(), 0, POSE_TEX_ARM_HEIGHT * tlFrame);
        tex.getPixelWriter().setPixels(POSE_TEX_FRAME_WIDTH / 2, 0,
                POSE_TEX_FRAME_WIDTH / 2, POSE_TEX_ARM_HEIGHT,
                ImageCache.IMAGES[trSprite].getPixelReader(), 0, POSE_TEX_ARM_HEIGHT * trFrame);
        tex.getPixelWriter().setPixels(0, POSE_TEX_ARM_HEIGHT,
                POSE_TEX_FRAME_WIDTH / 2, POSE_TEX_LEG_HEIGHT,
                ImageCache.IMAGES[lbSprite].getPixelReader(), 0, POSE_TEX_LEG_HEIGHT * lbFrame);
        tex.getPixelWriter().setPixels(POSE_TEX_FRAME_WIDTH / 2, POSE_TEX_ARM_HEIGHT,
                POSE_TEX_FRAME_WIDTH / 2, POSE_TEX_LEG_HEIGHT,
                ImageCache.IMAGES[rbSprite].getPixelReader(), 0, POSE_TEX_LEG_HEIGHT * rbFrame);
        return tex;
    }

    /**
     * Applies a color to the non-transparent pixels of a texture.
     * @param tex the texture to update.
     * @param color the color to update the texture with.
     */
    private static void applyTextureColor(WritableImage tex, Color color) {
        for (int x = 0; x < tex.getWidth(); x++) {
            for (int y = 0; y < tex.getHeight(); y++) {
                if (!tex.getPixelReader().getColor(x, y).equals(Color.TRANSPARENT)) {
                    tex.getPixelWriter().setColor(x, y, color);
                }
            }
        }
    }

}
