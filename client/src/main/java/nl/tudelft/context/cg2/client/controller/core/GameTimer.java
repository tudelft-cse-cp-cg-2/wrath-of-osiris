package nl.tudelft.context.cg2.client.controller.core;

import javafx.animation.AnimationTimer;
import nl.tudelft.context.cg2.client.model.Model;
import nl.tudelft.context.cg2.client.view.View;

/**
 * The GaneTimer class.
 * Starts a timer that dynamically updates the game.
 * First updates the game world stepwise.
 * Then updates the view based on the new state of the world.
 */
public class GameTimer extends AnimationTimer {

    private final Model model;
    private final View view;

    private long previousNanoTime;
    private long startNanoTime;

    /**
     * The graphics timer constructor.
     * @param model the model that holds the game data.
     * @param view the view that holds the game graphics.
     */
    public GameTimer(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Starts the graphics timer.
     */
    @Override
    public void start() {
        startNanoTime = System.nanoTime();
        previousNanoTime = startNanoTime;
        super.start();
    }

    /**
     * Called in every frame that is rendered by the javafx graphics thread.
     * @param currentNanoTime The timestamp of the current frame given in nanoseconds
     */
    @Override
    public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        double dt = (currentNanoTime - previousNanoTime) / 1000000000.0;
        this.update(t, dt);
        previousNanoTime = currentNanoTime;
    }

    /**
     * Updates everything that runs on the graphics timer.
     * @param t the passed time in s since timer initialization.
     * @param dt the passed time in s since the last update.
     */
    private void update(double t, double dt) {
        model.getWorld().step(t, dt);
        view.update();
    }

}