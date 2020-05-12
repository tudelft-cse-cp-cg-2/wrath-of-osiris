package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;

public class Wall extends Entity {

    public Wall(Image texture, Vector3D position, Vector3D velocity, Vector3D size) {
        super(texture, position, velocity, size);
    }

    @Override
    public void step(double t, double dt) {
        if(getPosition().z > 0) {
            setPosition(getPosition().add(getVelocity().mult(dt)));
        }
    }
}
