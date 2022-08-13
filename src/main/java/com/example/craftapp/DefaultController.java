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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DefaultController implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    public Button cartButton;
    @FXML
    private Button homeButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private SplitPane splitPane;
    @FXML
    private TilePane tilePane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane tileAnchor;
    @FXML
    private ImageView beerImage;
    @FXML
    private Label beerName;
    @FXML
    private Pane titlePane;
    private ShoppingCart shoppingCart;
    private ArrayList<Beer> listOfBeers = Main.listOfBeers;
    private ArrayList <CheckBox> listOfCheckBoxes;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;
        listOfCheckBoxes = new ArrayList<>();
        generateCheckBoxes();
        generateBeer(listOfBeers);

        // needed here, to avoid Pane stretching too much or too little
        tileAnchor.setPrefHeight(listOfBeers.size() / 2 *160);
        tilePane.setPrefRows(listOfBeers.size()/2);

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

            searchBar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    String text = searchBar.getText();
                }
            });
            searchButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

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

    //Populates the right side of SplitPane with Beer class objects and adds functionality from ItemController;
        private void generateBeer(ArrayList <Beer> list) {

            tilePane.getChildren().clear();
            tilePane.setPrefRows(list.size()/2);
            tileAnchor.setPrefHeight(list.size() / 2 *160);

            try {
            for (int i = 0; i < list.size(); i++) {
                FXMLLoader itemLoader = new FXMLLoader();
                itemLoader.setLocation(getClass().getResource("item.fxml"));
                Pane pane;
                pane = itemLoader.load();
                Pane itemPane = (Pane) pane.getChildren().get(0);

                beerName = (Label) itemPane.getChildren().get(2);
                beerImage = (ImageView) itemPane.getChildren().get(0);
                Button buyButton = (Button) itemPane.getChildren().get(1);

                beerName.setText(list.get(i).getName());
                buyButton.setText("€ " + list.get(i).getPrice());
                beerImage.setImage(list.get(i).getImage());

                buyButton.setId("buy" +i);
                buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            addToShoppingCart(mouseEvent, buyButton.getId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                itemPane = new Pane();

                itemPane.getChildren().addAll(beerImage,beerName, buyButton);
                itemPane.setId("pane" +i);

                tilePane.getChildren().add(itemPane);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);}
    }

    public void setCartButtonText() {

        double total = shoppingCart.getTotal();
        String toPrint = String.format("%.2f",total);

        cartButton.setText("€ " +toPrint);
        cartButton.setStyle("-fx-background-color:#facb89");

        System.out.println("THE TOTAL: " +shoppingCart.getTotal());
    }

    public void generateCheckBoxes() {

        double layoutX = 5;
        double layoutY = 10;
        double prefWidth = 80;
        double prefHeight = 20;

        TypesOfBeer[] arrayOfBeer = TypesOfBeer.values();
        for (int i = 0; i < arrayOfBeer.length; i++) {

            if (i > 0) {
                layoutY += prefHeight + prefHeight / 4;
            }
            CheckBox checkBox = new CheckBox();
            checkBox.setPrefWidth(prefWidth);
            checkBox.setPrefHeight(prefHeight);
            checkBox.setLayoutX(layoutX);
            checkBox.setLayoutY(layoutY);
            checkBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    filterCheckBoxChoice(event);
                }
            });
            checkBox.setText(String.valueOf(arrayOfBeer[i]));

            leftPane.getChildren().add(checkBox);
            listOfCheckBoxes.add(checkBox);
        }
    }
    void filterCheckBoxChoice(ActionEvent event) {

        ArrayList <Beer> filteredList = new ArrayList<>();
        boolean isChecked = false;

        for (CheckBox checkBox : listOfCheckBoxes) {
                    for (Beer beer : listOfBeers) {

                        if (checkBox.isSelected()) {
                            isChecked = true;
                            if (!filteredList.contains(beer) && checkBox.getText().equals(beer.getType())) {
                                filteredList.add(beer);
                            }
                        }
                    }
                }
        if (filteredList.isEmpty() && !isChecked) {
            generateBeer(listOfBeers);
        } else {
            generateBeer(filteredList);
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

    public void addToShoppingCart(MouseEvent event, String id) throws IOException {

        String replace = id.substring(0, 3);
        id = id.replace(replace, "");

        int index = Integer.parseInt(id);

        shoppingCart.add(listOfBeers.get(index));
        System.out.println(shoppingCart);
        setCartButtonText();
    }
}



