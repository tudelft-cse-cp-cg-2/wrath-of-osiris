package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

/**
 * Controller class representing the Controller in the View-Controller-Model design pattern.
 * It handles user input and sends it to the model.
 */
public class Controller {

    private Model model;
    private View view;

    public Controller() {

    }

    /**
     * Constructor for the Controller object.
     * @param model The Model object to send interaction data towards.
     * @param view The View object, containing the UI controls calling back to this controller.
     */
    public Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }

}
