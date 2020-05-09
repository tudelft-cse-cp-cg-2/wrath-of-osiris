package nl.tudelft.context.cg2.client.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.context.cg2.client.view.View;

/**
 * The application-wide model class.
 */
@SuppressFBWarnings()
public class Model {

    private View view;

    /**
     * Model constructor.
     * @param view The view that the model should command.
     */
    public Model(View view) {
        this.view = view;
    }

    /**
     * Loads the client data.
     */
    public void loadData() {
    }

}
