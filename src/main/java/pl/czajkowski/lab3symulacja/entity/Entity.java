package pl.czajkowski.lab3symulacja.entity;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.czajkowski.lab3symulacja.entity.movement.IMovementHandler;
import pl.czajkowski.lab3symulacja.entity.movement.MovementHandler;
import pl.czajkowski.lab3symulacja.entity.position.IPosition;
import pl.czajkowski.lab3symulacja.entity.position.Position;
import pl.czajkowski.lab3symulacja.entity.state.*;
import pl.czajkowski.lab3symulacja.exceptions.LeavingMapException;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;

import java.io.*;
import java.util.Map;
import java.util.Random;

public class Entity implements Externalizable {
    private static final long serialVersionUID = 65L;
    public final int circleRadius = SimulationConstants.CIRCLE_RADIUS;
    private IPosition position;
    private EntityState state;
    private Circle representation;
    private IMovementHandler movementHandler;
    private Pane pane;

    public Entity() {
    }

    public Entity(Pane pane, boolean hasEntitiesImmunity, boolean newEntity) {
        Random random = new Random();
        if (!newEntity) {
            if (hasEntitiesImmunity && random.nextInt(10) == 1) {
                state = new HealthyImmuneState(this);
            } else {
                state = new HealthyVulnerableState(this);
            }
        } else if (random.nextInt(10) == 1) {
            state = (random.nextBoolean()) ?
                    new InfectedNonSymptomaticState(this) : new InfectedSymptomaticState(this);
        } else {
            state = (hasEntitiesImmunity && random.nextBoolean()) ?
                    new HealthyImmuneState(this) : new HealthyVulnerableState(this);
        }

        position = new Position(
                random.nextInt(SimulationConstants.PANE_SIZE) + SimulationConstants.CIRCLE_RADIUS + 1,
                random.nextInt(SimulationConstants.PANE_SIZE) - SimulationConstants.CIRCLE_RADIUS + 1
        );
        representation = new Circle(circleRadius, state.getRepresentationColor());
        movementHandler = new MovementHandler();
        this.pane = pane;
        pane.getChildren().add(representation);
    }

    public void setPosition(double x, double y) {
        position.setComponents(x, y);
    }

    public void setPane(Pane pane) {
        this.pane = pane;
        representation = new Circle(circleRadius, state.getRepresentationColor());
        pane.getChildren().add(representation);
    }

    public IPosition getPosition() {
        return position;
    }

    public void setState(EntityState state) {
        this.state = state;
        representation.setFill(state.getRepresentationColor());
    }

    public EntityState getState() {
        return state;
    }

    public Circle getRepresentation() {
        return representation;
    }

    public void updatePosition() {
        representation.setTranslateX(position.getX());
        representation.setTranslateY(position.getY());
    }

    public void move() throws LeavingMapException {
        checkCollision();
        randomizeDirection();
        position.updateCoordinates(movementHandler);
    }

    public void checkInfections(Entity neighbour) {
        state.checkInfections(neighbour);
    }

    public void checkTime() {
        state.checkTime();
    }

    private void randomizeDirection() {
        Random random = new Random();
        if (random.nextInt(100) == 1) {
            movementHandler = new MovementHandler();
        }
    }

    private void checkCollision() {
        double x = position.getX();
        double y = position.getY();
        Random random = new Random();

        if (x < circleRadius || x > SimulationConstants.PANE_SIZE - circleRadius) {
            if (random.nextBoolean()) {
                pane.getChildren().remove(getRepresentation());
                throw new LeavingMapException();
            } else {
                movementHandler.changeDirectionX();
            }
        } else if (y < circleRadius || y > SimulationConstants.PANE_SIZE - circleRadius) {
            if (random.nextBoolean()) {
                pane.getChildren().remove(getRepresentation());
                throw new LeavingMapException();
            } else {
                movementHandler.changeDirectionY();
            }
        }
    }

    public void setItselfToState() {
        state.setEntity(this);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(position);
        out.writeObject(state);
        out.writeObject(movementHandler);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        position = (IPosition) in.readObject();
        state = (EntityState) in.readObject();
        movementHandler = (IMovementHandler) in.readObject();
    }
}
