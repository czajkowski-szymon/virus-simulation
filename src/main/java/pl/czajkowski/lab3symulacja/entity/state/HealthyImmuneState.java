package pl.czajkowski.lab3symulacja.entity.state;

import javafx.scene.paint.Color;
import pl.czajkowski.lab3symulacja.entity.Entity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class HealthyImmuneState extends EntityState {
    public HealthyImmuneState() {
        representationColor = Color.BLUE;
    }

    public HealthyImmuneState(Entity entity) {
        super(entity);
        representationColor = Color.BLUE;
    }

    @Override
    public void checkInfections(Entity neighbour) {}

    @Override
    public void checkTime() {}
}
