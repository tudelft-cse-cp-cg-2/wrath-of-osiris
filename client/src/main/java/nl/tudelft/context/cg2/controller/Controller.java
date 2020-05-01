package nl.tudelft.context.cg2.controller;

import nl.tudelft.context.cg2.controller.core.GraphicsTimer;
import nl.tudelft.context.cg2.model.Model;
import nl.tudelft.context.cg2.view.View;

public class Controller {

    private final GraphicsTimer graphicsTimer;

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.graphicsTimer = new GraphicsTimer(view);
        this.view = view;
        this.model = model;
    }

    /**
     * The graphics timer getter.
     * @return the graphics timer.
     */
    public GraphicsTimer getGraphicsTimer() {
        return graphicsTimer;
    }
}
