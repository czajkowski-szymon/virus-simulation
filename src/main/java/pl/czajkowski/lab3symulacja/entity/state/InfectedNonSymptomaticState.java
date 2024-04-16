package pl.czajkowski.lab3symulacja.entity.state;

import javafx.scene.paint.Color;
import pl.czajkowski.lab3symulacja.entity.Entity;

public class InfectedNonSymptomaticState extends EntityState {
    public InfectedNonSymptomaticState() {
        representationColor = Color.WHITE;
    }

    public InfectedNonSymptomaticState(Entity entity) {
        super(entity);
        representationColor = Color.WHITE;
    }

    @Override
    public void checkInfections(Entity neighbour) {}

    @Override
    public void checkTime() {
        if (secondToImmunity <= 0) {
            entity.setState(new HealthyImmuneState(entity));
        } else {
            secondToImmunity -= 1;
        }
    }
}
