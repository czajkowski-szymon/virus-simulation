package pl.czajkowski.lab3symulacja.entity.state;

import javafx.scene.paint.Color;
import pl.czajkowski.lab3symulacja.entity.Entity;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

import java.io.*;
import java.util.Random;

public abstract class EntityState implements Externalizable {
    protected Entity entity;
    protected Color representationColor;
    protected int secondToImmunity;

    public EntityState() {}

    public EntityState(Entity entity) {
        this.entity = entity;
        secondToImmunity = (new Random().nextInt(11) + 20) * (int) SimulationConstants.FPS;
    }

    public Color getRepresentationColor() {
        return representationColor;
    }

    public abstract void checkInfections(Entity neighbour);
    public abstract void checkTime();

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(secondToImmunity);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        secondToImmunity = in.readInt();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
