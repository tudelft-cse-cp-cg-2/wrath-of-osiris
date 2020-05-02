package nl.tudelft.context.cg2.client.controller;

import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

public class Controller {

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }
}
