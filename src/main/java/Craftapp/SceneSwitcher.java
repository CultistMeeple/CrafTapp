package Craftapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneSwitcher {
    private final int width;
    private final int height;
    private final String css;
    private Stage stage;

    public SceneSwitcher(int width, int height) {
        this.width = width;
        this.height = height;
        css = Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
    }

    public void switchScene (String target) {
        String file = getFxml(target);
        if (file != null) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
                Scene scene = new Scene(root, width, height);
                scene.getStylesheets().add(css);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String getFxml (String target) {
        return switch (target) {
            case "home" -> "default.fxml";
            case "results" -> "results.fxml";
            case "cart" -> "cart.fxml";
            case "add" -> "addEntry.fxml";
            default -> null;
        };
    }
}
