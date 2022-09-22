package Craftapp;

import Craftapp.domain.Beer.Beer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private ShoppingCart shoppingCart;
    private Searcher <Beer> beerSearcher;
    private ChoiceBox <String> choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private TextField searchBar;
    private Button cartButton;
    private ArrayList<Beer> listOfBeers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;
        beerSearcher = Main.beerSearcher;
        listOfBeers = Main.listOfBeers;

        //Sets up navigationPane, with searchBar and navigation buttons.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("navigation.fxml"));
            Pane vBox = loader.load();
            vBox = (VBox) vBox.getChildren().get(0);
            HBox hBox = (HBox) vBox.getChildren().get(0);
            HBox hBox1 = (HBox) vBox.getChildren().get(1);

            choiceBox = (ChoiceBox<String>)hBox1.getChildren().get(2);
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
            removeButton.setOnAction(event -> {
                shoppingCart.remove(beer);
                generate();
            });

            //Makes the button round;
            /*
            double r = 12;
            removeButton.setShape(new Circle(r));
            removeButton.setMinSize(r*2, r*2);
            removeButton.setMaxSize(r*2, r*2);

             */

            removeButton.getStyleClass().add("remove-button");

            Button plusButton = (Button) buttons.getChildren().get(0);
            TextField countField = (TextField) buttons.getChildren().get(1);
            plusButton.setOnAction(event -> {
                shoppingCart.add(beer);
                countField.setText(String.valueOf(shoppingCart.getCount(beer)));
                generate();
            });
            countField.setText(String.valueOf(shoppingCart.getCount(beer)));

            countField.setOnKeyTyped(keyEvent -> {
                int count = 0;
                try {
                    count = Integer.parseInt(countField.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                shoppingCart.setCount(beer,count);
                generate();
            });

            Button minusButton = (Button) buttons.getChildren().get(2);
            minusButton.setOnAction(event -> {
                shoppingCart.decrease(beer);
                countField.setText(String.valueOf(shoppingCart.getCount(beer)));
                generate();
            });

            itemHolder.getStyleClass().add("item");
            contentsView.getChildren().add(itemHolder);
        }

        contentsView.setSpacing(10);

        //Updates the text on cartButton;
        setCartButtonText();
    }

    public void setCartButtonText() {
        double total = shoppingCart.getTotal();
        String toPrint = String.format("%.2f",total);
        cartButton.setText("€ " +toPrint);
    }
}
