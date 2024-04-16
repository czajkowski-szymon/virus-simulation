package pl.czajkowski.lab3symulacja.entity.state;

import javafx.scene.paint.Color;
import pl.czajkowski.lab3symulacja.entity.Entity;

public class InfectedSymptomaticState extends EntityState {
    public InfectedSymptomaticState() {
        representationColor = Color.PURPLE;
    }

    public InfectedSymptomaticState(Entity entity) {
        super(entity);
        representationColor = Color.PURPLE;
    }

    @Override
    public void checkInfections(Entity neighbour) {
    }

    @Override
    public void checkTime() {
        if (secondToImmunity <= 0) {
            entity.setState(new HealthyImmuneState(entity));
        } else {
            secondToImmunity--;
        }
    }
}
