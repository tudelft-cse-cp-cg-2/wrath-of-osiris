package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;

/**
 * The wall class.
 * Features the wall entity in the game world.
 */
public class Wall extends Entity {

    private boolean decayed;
    private double timer;

    /**
     * The wall constructor.
     * @param texture the wall texture.
     * @param position the wall position within the world.
     * @param velocity the wall velocity within the world.
     * @param size the wall size within the world.
     */
    public Wall(Image texture, Vector3D position, Vector3D velocity, Vector3D size) {
        super(texture, null, position, velocity, size);
        this.decayed = false;
        this.timer = 0D;
    }

    @Override
    public void step(double t, double dt) {
        if (getPosition().z > 0) {
            setPosition(getPosition().add(getVelocity().mult(dt)));
        } else if (!decayed) {
            timer += dt;

            if (timer > 0.05D) {
                timer = 0D;
                decayed = true;
            }
        }
    }

    /**
     * A boolean to check if the wall has decayed.
     * The wall should be removed from the game when decayed.
     * @return true or false.
     */
    public boolean hasDecayed() {
        return decayed;
    }
}
