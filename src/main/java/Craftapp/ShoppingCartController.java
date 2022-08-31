package Craftapp;

import Craftapp.domain.Beer.Beer;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable { 
    @FXML
    private VBox contentsView;
    @FXML
    private Pane navigationPane;
    @FXML
    private Pane titlePane;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ShoppingCart shoppingCart;
    private Searcher <Beer> beerSearcher;
    private ChoiceBox <String> choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private TextField searchBar;
    private Button searchButton;
    private Button cartButton;
    private ArrayList<Beer> listOfBeers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;
        beerSearcher = Main.beerSearcher;
        listOfBeers = Main.listOfBeers;

        Button homeButton;
        //Sets up navigationPane, with searchBar and navigation buttons.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("navigation.fxml"));
            Pane vBox = loader.load();
            vBox = (VBox) vBox.getChildren().get(0);
            HBox hBox = (HBox) vBox.getChildren().get(0);
            HBox hBox1 = (HBox) vBox.getChildren().get(1);

            Button addButton = (Button)hBox1.getChildren().get(1);
            homeButton = (Button)hBox1.getChildren().get(0);
            choiceBox = (ChoiceBox<String>)hBox1.getChildren().get(2);
            searchButton = (Button)hBox.getChildren().get(1);
            searchBar = (TextField) hBox.getChildren().get(0);
            navigationPane.getChildren().add(vBox);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        choiceBox.getItems().addAll(sort);
        choiceBox.setValue(sort[0]);
        
        /*
        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ArrayList<Beer> list = beerSearcher.getResults();

                generateResults(list);
            }
        });
        
         */

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

        searchBar.setText(beerSearcher.getPhrase());
        searchBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
                try {
                    switchToResults(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
                try {
                    switchToResults(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        generate();
    }
    public void generate () {

        contentsView.getChildren().clear();
        HashMap <Beer, Integer> contents = shoppingCart.getContents();

        for (Beer beer : contents.keySet()) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cartItem.fxml"));
        HBox itemHolder;
            try {
                itemHolder = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ImageView image = (ImageView) itemHolder.getChildren().get(0);
            image.setImage(beer.getImage());

            VBox control = (VBox) itemHolder.getChildren().get(1);
            Label nameLabel = (Label)control.getChildren().get(0);
            nameLabel.setText(beer.getName());
            Label priceLabel = (Label)control.getChildren().get(1);
            String price = String.format("%.2f", beer.getPrice());
            priceLabel.setText("€ " +price);
            HBox buttons = (HBox) control.getChildren().get(2);
            Button removeButton = (Button)buttons.getChildren().get(3);
            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    shoppingCart.remove(beer);
                    generate();
                }
            });

            //Makes the button round;
            double r = 12;
            removeButton.setShape(new Circle(r));
            removeButton.setMinSize(r*2, r*2);
            removeButton.setMaxSize(r*2, r*2);

            Button plusButton = (Button) buttons.getChildren().get(0);
            TextField countField = (TextField) buttons.getChildren().get(1);
            plusButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    shoppingCart.add(beer);
                    countField.setText(String.valueOf(shoppingCart.getCount(beer)));
                    generate();
                }

            });
            countField.setText(String.valueOf(shoppingCart.getCount(beer)));

            countField.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    int count = 0;
                    try {
                        count = Integer.parseInt(countField.getText());
                    } catch (Exception e) {
                                            }
                    shoppingCart.setCount(beer,count);
                    generate();
                }
            });

            Button minusButton = (Button) buttons.getChildren().get(2);
            minusButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    shoppingCart.decrease(beer);
                    countField.setText(String.valueOf(shoppingCart.getCount(beer)));
                    generate();
                }
            });

            itemHolder.setStyle("-fx-background-color: white");
            itemHolder.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box,#00000033,10,0.0,0,0)");
            contentsView.getChildren().add(itemHolder);
        }

        contentsView.setSpacing(10);

        //Updates the text on cartButton;
        setCartButtonText();
    }


    public void setCartButtonText() {

        double total = shoppingCart.getTotal();

        if (total > 0) {
            cartButton.setStyle("-fx-background-color:#66FF66");

        } else {
            cartButton.setStyle("");
        }

        String toPrint = String.format("%.2f",total);

        cartButton.setText("€ " +toPrint);
    }

    private void search() {
        String input = searchBar.getText();
        input = input.trim();
        //Clear to reset the internal list.
        beerSearcher.clear();
        beerSearcher.setPhrase(input);
        beerSearcher.searchList(listOfBeers);
    }

    public void switchToResults (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("results.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToHome (ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("default.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAddBeer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addEntry.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    void switchToAddBrewery(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addBrewery.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    void switchToAddShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addShop.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

}
