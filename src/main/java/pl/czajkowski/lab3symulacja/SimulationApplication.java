package pl.czajkowski.lab3symulacja;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.czajkowski.lab3symulacja.entity.Entity;
import pl.czajkowski.lab3symulacja.helpers.SimulationConstants;
import pl.czajkowski.lab3symulacja.simulation.Simulation;

import java.io.*;

public class SimulationApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(SimulationApplication.class.getResource("simulation-view.fxml"));
        Scene scene =
                new Scene(fxmlLoader.load(), SimulationConstants.PANE_SIZE, SimulationConstants.PANE_SIZE + 100);
        stage.setTitle("Symulacja");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}