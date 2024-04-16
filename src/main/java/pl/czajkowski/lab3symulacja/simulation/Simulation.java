package pl.czajkowski.lab3symulacja.simulation;

import javafx.scene.layout.Pane;
import pl.czajkowski.lab3symulacja.entity.Entity;
import pl.czajkowski.lab3symulacja.entity.movement.IMovementHandler;
import pl.czajkowski.lab3symulacja.entity.movement.MovementHandler;
import pl.czajkowski.lab3symulacja.entity.position.IPosition;
import pl.czajkowski.lab3symulacja.entity.position.Position;
import pl.czajkowski.lab3symulacja.exceptions.LeavingMapException;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

import java.io.*;
import java.util.*;

public class Simulation implements Externalizable {
    private List<Entity> population = new ArrayList<>();
    private boolean hasEntitiesImmunity;
    private Pane pane;

    public Simulation() {
    }

    public Simulation(Pane pane, int populationSize, boolean hasEntitiesImmunity) {
        this.hasEntitiesImmunity = hasEntitiesImmunity;
        this.pane = pane;
        for (int i = 0; i < populationSize; i++) {
            population.add(new Entity(pane, this.hasEntitiesImmunity, false));
        }
        refreshPopulation();
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public int getPopulationSize() {
        return population.size();
    }

    public void movePopulation() {
        for (Iterator<Entity> i = population.iterator(); i.hasNext(); ) {
            Entity entity = i.next();
            try {
                entity.move();
            } catch (LeavingMapException e) {
                i.remove();
            }
        }
    }

    public void spawnNewEntities(Pane pane) {
        Random random = new Random();
        if (random.nextInt(4) == 1) {
            Entity entity = new Entity(pane, hasEntitiesImmunity, true);
            int x = random.nextInt(SimulationConstants.PANE_SIZE) + 1;
            int y = random.nextInt(SimulationConstants.PANE_SIZE) + 1;
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    entity.setPosition(SimulationConstants.CIRCLE_RADIUS, y - SimulationConstants.CIRCLE_RADIUS);
                } else {
                    entity.setPosition(
                            SimulationConstants.PANE_SIZE - SimulationConstants.CIRCLE_RADIUS,
                            y - SimulationConstants.CIRCLE_RADIUS
                    );
                }
            } else {
                if (random.nextBoolean()) {
                    entity.setPosition(x - SimulationConstants.CIRCLE_RADIUS, SimulationConstants.CIRCLE_RADIUS);
                } else {
                    entity.setPosition(
                            x - SimulationConstants.CIRCLE_RADIUS,
                            SimulationConstants.PANE_SIZE - SimulationConstants.CIRCLE_RADIUS
                    );
                }
            }
            population.add(entity);
        }
        refreshPopulation();
    }

    public void refreshPopulation() {
        for (Entity entity : population) {
            entity.updatePosition();
        }
    }

    public SimulationMemento createMemento() {
        return new SimulationMemento(population);
    }

    public void restore(SimulationMemento memento) {
        this.population = memento.getPopulation();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(population);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        population = (List<Entity>) in.readObject();
    }

    public void setPaneForEntities() {
        for(Entity entity : population) {
            entity.setPane(pane);
        }
    }

    public void setEntitiesToStates() {
        for (Entity entity : population) {
            entity.setItselfToState();
        }
    }

    public void checkInfections() {
        for (Entity entity : population) {
            for (Entity neighbour : population) {
                if (entity != neighbour) {
                    entity.checkInfections(neighbour);
                }
            }
        }
    }

    public void resetInfectionTimers() {
        for (Entity entity : population) {
            entity.checkTime();
        }
    }

    public static class SimulationMemento implements Externalizable  {
        private List<Entity> population = new ArrayList<>();

        public SimulationMemento() {}

        private SimulationMemento(List<Entity> population) {
            this.population = population;
        }

        public List<Entity> getPopulation() {
            return population;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(population);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            population = (List<Entity>) in.readObject();
        }
    }
}
