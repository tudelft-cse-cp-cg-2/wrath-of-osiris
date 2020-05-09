package nl.tudelft.context.cg2.client.controller;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;



/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
@SuppressFBWarnings()
@SuppressWarnings("VisibilityModifier")
public abstract class AbstractController {

    protected View view;
    protected Model model;

    /**
     * Initializes the Controller object.
     * @param model The Model object to send interaction data towards.
     * @param view The View object, containing the UI controls calling back to this controller.
     */
    public void init(@NonNull Model model, @NonNull View view) {
        this.view = view;
        this.model = model;
    }

}
