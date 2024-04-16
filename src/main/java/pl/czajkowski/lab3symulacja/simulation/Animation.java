package pl.czajkowski.lab3symulacja.simulation;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;
import pl.czajkowski.lab3symulacja.simulation.Simulation.SimulationMemento;

import java.io.*;
import java.time.LocalDateTime;

public class Animation extends AnimationTimer {
    private final long interval = 1000000000L / SimulationConstants.FPS;
    private Simulation simulation;
    private Pane pane;
    private long last = 0;
    private String mementosDirectory = "mementos/";
    private long elapsedFrames = 0L;

    public Animation(Simulation simulation, Pane pane) {
        this.simulation = simulation;
        this.pane = pane;
    }

    @Override
    public void handle(long now) {
        if (now - last > interval) {
            simulation.movePopulation();
            simulation.checkInfections();
            simulation.resetInfectionTimers();
            simulation.spawnNewEntities(pane);

//            if (elapsedFrames-- % 25 == 0) {
//                save();
//            }

            last = now;
        }
    }

    public void save() {
        String name = newMementoFileName();
        try (final FileOutputStream fout = new FileOutputStream(mementosDirectory + name + ".txt");
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(simulation.createMemento());
            out.flush();
            System.out.println("Object " + simulation + " has been saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Simulation restore(Pane pane, String fileName) {
        SimulationMemento simulationMemento;
        Simulation restoredSimulation = new Simulation();
        try (final FileInputStream file = new FileInputStream(mementosDirectory + fileName);
             final ObjectInputStream in = new ObjectInputStream(file)) {
            simulationMemento = (SimulationMemento) in.readObject();
            restoredSimulation.restore(simulationMemento);
            restoredSimulation.setPane(pane);
            restoredSimulation.setPaneForEntities();
            restoredSimulation.setEntitiesToStates();
            restoredSimulation.refreshPopulation();
            in.close();
            file.close();
            System.out.println("Object has been deserialized " + restoredSimulation);
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return restoredSimulation;
    }

    private String newMementoFileName() {
        String name = "simulation" + LocalDateTime.now();
        return name.substring(0, 29).replaceAll(":","");
    }
}
