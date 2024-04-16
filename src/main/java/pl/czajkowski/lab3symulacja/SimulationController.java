package pl.czajkowski.lab3symulacja;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;
import pl.czajkowski.lab3symulacja.simulation.Animation;
import pl.czajkowski.lab3symulacja.simulation.Simulation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimulationController {
    private int paneSize = SimulationConstants.PANE_SIZE;
    @FXML
    private Pane pane;
    @FXML
    private Button spawn;
    @FXML
    private Button start;
    @FXML
    private Button stop;
    @FXML
    private Button save;
    @FXML
    private Button restore;
    @FXML
    private ComboBox<String> comboBox;
    private Simulation simulation;
    private final int populationSize = SimulationConstants.POPULATION_SIZE;
    private Animation animation;

    @FXML
    public void initialize() {
        animation = new Animation(simulation, pane);
        ObservableList<String> cases = FXCollections.observableArrayList();
        cases.add("poczatkowa populacja oraz losowane osobniki nie posiadaja odpornosci");
        cases.add("czesc poczatkowej populacji oraz wylosowanych osobników posiada odpornosc");
        comboBox.setItems(cases);
    }

    @FXML
    public void spawn() {
        pane.getChildren().clear();
        if (comboBox.getValue().equals("poczatkowa populacja oraz losowane osobniki nie posiadaja odpornosci")) {
            simulation = new Simulation(pane, populationSize, false);
        } else if (comboBox.getValue().equals("czesc poczatkowej populacji oraz wylosowanych osobników posiada odpornosc")) {
            simulation = new Simulation(pane, populationSize, true);
        }
        animation = new Animation(simulation, pane);
        start.setDisable(false);
        stop.setDisable(true);
    }

    @FXML
    public void start() {
        animation.start();
        start.setDisable(true);
        spawn.setDisable(true);
        stop.setDisable(false);
        restore.setDisable(true);
        save.setDisable(true);
        comboBox.setDisable(true);
    }

    @FXML
    public void stop() {
        animation.stop();
        stop.setDisable(true);
        spawn.setDisable(false);
        start.setDisable(false);
        save.setDisable(false);
        restore.setDisable(false);
        comboBox.setDisable(false);
    }

    @FXML
    public void save() {
        animation.save();
    }

    @FXML
    public void restore() {
        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File(".\\src\\main\\resources\\mementos");
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }

        pane.getChildren().clear();
        simulation = animation.restore(pane, selectedFile.getName());
        animation = new Animation(simulation, pane);
    }
}