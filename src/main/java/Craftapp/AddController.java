package Craftapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private TextField beerBrewery;
    @FXML
    private ImageView beerMug;
    @FXML
    private TextField beerName;
    @FXML
    private TextField beerType;
    @FXML
    private Button homeButton;
    @FXML
    private TextField searchBar;
    @FXML
    private TextField breweryAdress;
    @FXML
    private TextField breweryWebsite;
    @FXML
    private Button searchButton;
    @FXML
    private Button submitButton;
    @FXML
    private RadioButton radioBeer;
    @FXML
    private RadioButton radioBrewery;
    @FXML
    private RadioButton radioShop;
    @FXML
    private TextField shopAdress;
    @FXML
    private TextField shopCity;
    @FXML
    private TextField shopCountry;
    @FXML
    private TextField shopName;
    @FXML
    private TextField shopWebsite;
    @FXML
    private ImageView cartIcon;
    @FXML
    private Button cartButton;

    @FXML
    private Label title;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TreeView<String> treeView;
    @FXML
    void selectItem(MouseEvent event) {

        TreeItem<String>item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            System.out.println(item.getValue());
        }
    }
    @FXML
    public void switchToHome (ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("default.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToAddBeer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addEntry.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void switchToAddBrewery(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addBrewery.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToAddShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addShop.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addToShoppingCart(MouseEvent event) {

    }

    @FXML
    void switchToCart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("cart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> treeRoot = new TreeItem<>("Latvia");
        TreeItem <String> branch1 = new TreeItem<>("Breweries");
        TreeItem <String> branch2 = new TreeItem<>("Beers");

        if (treeView != null) {
            treeView.setRoot(treeRoot);
            treeRoot.getChildren().add(branch1);
            branch1.getChildren().add(branch2);
        }

        searchBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String text = searchBar.getText();
                title.setText(text);
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                title.setText("Button");
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    switchToAddBeer(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

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
    }

}
