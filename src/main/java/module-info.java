module com.example.craftapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    //requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.craftapp to javafx.fxml;
    exports com.example.craftapp;
}