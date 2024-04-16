package pl.czajkowski.lab3symulacja.entity.position;

import pl.czajkowski.lab3symulacja.entity.movement.IMovementHandler;
import pl.czajkowski.lab3symulacja.entity.movement.MovementHandler;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

public class Position implements IPosition {
    private double x;
    private double y;

    public Position() {}

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setComponents(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void updateCoordinates(IMovementHandler movementHandler) {
        x += movementHandler.getDirectionX() / 25 * SimulationConstants.METER;
        y += movementHandler.getDirectionY() / 25 * SimulationConstants.METER;
    }

    @Override
    public double distance(IPosition position) {
        return Math.sqrt(
                (x - position.getX())*(x - position.getX()) + (y - position.getY())*(y - position.getY())
        );
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
