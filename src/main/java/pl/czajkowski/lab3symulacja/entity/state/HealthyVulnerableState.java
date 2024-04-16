package pl.czajkowski.lab3symulacja.entity.state;

import javafx.scene.paint.Color;
import pl.czajkowski.lab3symulacja.entity.Entity;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class HealthyVulnerableState extends EntityState {
    private int timeToInfection;
    private boolean sickNotCounted;

    public HealthyVulnerableState() {
        representationColor = Color.GREEN;
    }

    public HealthyVulnerableState(Entity entity) {
        super(entity);
        representationColor = Color.GREEN;
        sickNotCounted = true;
    }

    @Override
    public void checkInfections(Entity neighbour) {
        Random random = new Random();
        double distance = entity.getPosition().distance(neighbour.getPosition());
        boolean isNearby = distance < 2 * SimulationConstants.METER + SimulationConstants.CIRCLE_RADIUS;
        boolean isInfected = neighbour.getState() instanceof InfectedSymptomaticState
                       || neighbour.getState() instanceof InfectedNonSymptomaticState;
        if (isNearby && isInfected) {
            count();
            if (timeToInfection <= 0) {
                count();
                if (neighbour.getState() instanceof InfectedSymptomaticState || random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        entity.setState(new InfectedSymptomaticState(entity));
                    } else {
                        entity.setState(new InfectedNonSymptomaticState(entity));
                    }
                }
            }
        }
    }

    private void count() {
        if (sickNotCounted) {
            sickNotCounted = false;
            timeToInfection--;
        }
    }

    public void checkTime() {
        if (sickNotCounted) {
            timeToInfection = SimulationConstants.TIME_TO_INFECTION;
        } else {
            sickNotCounted = true;
        }
    }
}
