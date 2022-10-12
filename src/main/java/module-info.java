module com.example.craftapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    //requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens Craftapp to javafx.fxml;

    exports Craftapp.domain;
    opens Craftapp.domain to javafx.fxml;
    exports Craftapp.domain.Beer;
    opens Craftapp.domain.Beer to javafx.fxml;
    exports Craftapp.domain.Brewery;
    opens Craftapp.domain.Brewery to javafx.fxml;
    exports Craftapp.util;
    opens Craftapp.util to javafx.fxml;
    exports Craftapp.control;
    opens Craftapp.control to javafx.fxml;
}