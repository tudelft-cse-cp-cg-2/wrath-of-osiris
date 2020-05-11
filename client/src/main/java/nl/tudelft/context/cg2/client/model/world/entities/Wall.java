package nl.tudelft.context.cg2.client.model.world.entities;

import javafx.scene.image.Image;
import nl.tudelft.context.cg2.client.model.datastructures.Vector3D;
import nl.tudelft.context.cg2.client.model.world.Entity;

public class Wall extends Entity {

    private int counter;

    public Wall(Image texture, Vector3D position, Vector3D velocity, Vector3D size) {
        super(texture, position, velocity, size);
        counter = 0;
    }

    @Override
    public void step(double t, double dt) {
        counter++;
        if(counter < 200) {
            setPosition(getPosition().add(new Vector3D(0, 2, 0)));
        } else if(counter < 400) {
            setPosition(getPosition().add(new Vector3D(0, -2, 0)));
        } else {
            counter = 0;
        }
    }
}
