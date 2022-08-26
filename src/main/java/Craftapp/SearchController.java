package Craftapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private Pane navigationPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane tileAnchor;
    @FXML
    private TilePane tilePane;
    @FXML
    private Pane titlePane;
    private ChoiceBox choiceBox;
    private ShoppingCart shoppingCart;
    private Button cartButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;

        Button homeButton;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("navigation.fxml"));
            Pane parent = loader.load();
            parent = (Pane)parent.getChildren().get(0);
            Button searchButton = (Button)parent.getChildren().get(0);
            Button addButton = (Button)parent.getChildren().get(1);
            homeButton = (Button)parent.getChildren().get(2);
            choiceBox = (ChoiceBox<String>)parent.getChildren().get(3);
            TextField searchBar = (TextField) parent.getChildren().get(4);
            navigationPane.getChildren().addAll(searchButton, addButton, homeButton, choiceBox, searchBar);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    switchToHome(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        choiceBox.getItems().addAll(sort);
        choiceBox.setValue(sort[0]);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("title.fxml"));
            Pane pane = loader.load();
            pane = (Pane)pane.getChildren().get(0);
            titlePane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Pane testPane = (Pane) titlePane.getChildren().get(0);
        cartButton = (Button) testPane.getChildren().get(2);
        setCartButtonText();


    }

    public void setCartButtonText() {

        double total = shoppingCart.getTotal();
        String toPrint = String.format("%.2f",total);

        cartButton.setText("€ " +toPrint);

        if (shoppingCart.getTotal() > 0) {
            cartButton.setStyle("-fx-background-color:#facb89");
        }
    }

    public void switchToHome (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("default.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }
}
