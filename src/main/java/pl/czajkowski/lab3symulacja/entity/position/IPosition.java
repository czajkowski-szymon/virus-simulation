package pl.czajkowski.lab3symulacja.entity.position;

import pl.czajkowski.lab3symulacja.entity.movement.IMovementHandler;
import pl.czajkowski.lab3symulacja.entity.movement.MovementHandler;

import java.io.Serializable;

public interface IPosition extends Serializable {
    double getX();
    double getY();
    void setComponents(double x, double y);
    void updateCoordinates(IMovementHandler movementHandler);
    double distance(IPosition position);
}
