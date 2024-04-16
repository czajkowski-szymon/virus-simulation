package pl.czajkowski.lab3symulacja.entity.movement;

import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

public class MovementHandler implements IMovementHandler {
    private double velocity = SimulationConstants.MAX_VELOCITY;
    private double direction[] = new double[2];
    public MovementHandler() {
        direction[0] = Math.sin(Math.random() * 2 * Math.PI) * Math.random() * velocity;
        direction[1] = Math.cos(Math.random() * 2 * Math.PI) * Math.random() * velocity;
    }

    @Override
    public double getDirectionX() {
        return direction[0];
    }

    @Override
    public double getDirectionY() {
        return direction[1];
    }

    @Override
    public void changeDirectionX() {
        direction[0] *= -1;
    }

    @Override
    public void changeDirectionY() {
        direction[1] *= -1;
    }
}
