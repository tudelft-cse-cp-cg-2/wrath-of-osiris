package nl.tudelft.context.cg2.client.view.elements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Features a heart graphic in the UI.
 */
public class Heart extends ImageView {

    private Image active;
    private Image inactive;

    /**
     * The Heart image constructor.
     * @param active active heart sprite.
     * @param inactive inactive heart sprite.
     */
    public Heart(Image active, Image inactive) {
        super(active);
        this.active = active;
        this.inactive = inactive;
    }

    /**
     * Sets the displayed sprite to the active sprite.
     */
    public void activate() {
        this.setImage(active);
    }

    /**
     * Sets the displayed sprite to the inactive sprite.
     */
    public void deactivate() {
        this.setImage(inactive);
    }

}
