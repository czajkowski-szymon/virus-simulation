package pl.czajkowski.lab3symulacja.entity.movement;

import java.io.Serializable;

public interface IMovementHandler extends Serializable {
    double getDirectionX();
    double getDirectionY();
    void changeDirectionX();
    void changeDirectionY();
}
