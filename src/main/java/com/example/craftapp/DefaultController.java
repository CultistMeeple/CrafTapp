package com.example.craftapp;

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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DefaultController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button beerBuy1;
    @FXML
    private Button beerBuy2;
    @FXML
    private Button beerBuy3;
    @FXML
    private Button beerBuy4;
    @FXML
    private Button beerBuy5;
    @FXML
    private Button beerBuy6;
    @FXML
    private Button beerBuy7;
    @FXML
    private Button beerBuy8;
    @FXML
    private ImageView beerImage1;
    @FXML
    private ImageView beerImage2;
    @FXML
    private ImageView beerImage3;
    @FXML
    private ImageView beerImage4;
    @FXML
    private ImageView beerImage5;
    @FXML
    private ImageView beerImage6;
    @FXML
    private ImageView beerImage7;
    @FXML
    private ImageView beerImage8;
    @FXML
    private Label beerLabel1;
    @FXML
    private Label beerLabel2;
    @FXML
    private Label beerLabel3;
    @FXML
    private Label beerLabel4;
    @FXML
    private Label beerLabel5;
    @FXML
    private Label beerLabel6;
    @FXML
    private Label beerLabel7;
    @FXML
    private Label beerLabel8;
    @FXML
    private ImageView beerMug;
    @FXML
    private Button cartButton;
    @FXML
    private ImageView cartIcon;
    @FXML
    private CheckBox checkBox0;
    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;
    @FXML
    private CheckBox checkBox5;
    @FXML
    private CheckBox checkBox6;
    @FXML
    private Button homeButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Label title;
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void filterCheckBoxChoice(ActionEvent event) {

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

        BreweryLatvia malduguns = new BreweryLatvia("Malduguns");
        BeerIPA sanslide = new BeerIPA("Sanslide", malduguns,3.62);
        sanslide.setImage("sanslide.jpg");

        beerImage2.setImage(sanslide.getImage());
        beerLabel2.setText(sanslide.getName());

        checkBox0.setText(String.valueOf(TypesOfBeer.IPA));
        checkBox1.setText(String.valueOf(TypesOfBeer.Lager));
        checkBox2.setText(String.valueOf(TypesOfBeer.Stout));
        checkBox3.setText(String.valueOf(TypesOfBeer.Porter));
        checkBox4.setText(String.valueOf(TypesOfBeer.Other));

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
