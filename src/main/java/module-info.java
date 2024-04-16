module pl.czajkowski.lab3symulacja {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.czajkowski.lab3symulacja to javafx.fxml;
    exports pl.czajkowski.lab3symulacja;
}