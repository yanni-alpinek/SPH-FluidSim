module yanni.fluidsimulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens yanni.fluidsimulation to javafx.fxml;
    exports yanni.fluidsimulation;
    exports yanni.fluidsimulation.view;
    opens yanni.fluidsimulation.view to javafx.fxml;
    exports yanni.fluidsimulation.model;
    opens yanni.fluidsimulation.model to javafx.fxml;
}