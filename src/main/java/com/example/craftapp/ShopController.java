package com.example.craftapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    @FXML
    private Button cartButton;
    private Scene scene;
    private Parent root;
    private ShoppingCart shoppingCart;
    @FXML
    private Button buyButton;
    private ArrayList<Beer> listOfBeers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOfBeers = Main.listOfBeers;
        shoppingCart = Main.shoppingCart;
    }

    @FXML
    void switchToCart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("cart.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToHome(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("default.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335, 600);
        stage.setScene(scene);
        stage.show();
    }

}