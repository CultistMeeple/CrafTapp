package Craftapp.control;

import Craftapp.domain.Beer.Beer;
import Craftapp.util.Searcher;
import Craftapp.util.ShoppingCart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    public ScrollPane scrollPane;
    @FXML
    private VBox contentsView;
    @FXML
    private Pane navigationPane;
    @FXML
    private Pane titlePane;
    private ShoppingCart shoppingCart;
    private ChoiceBox <String> choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;

        //Sets up navigationPane
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Craftapp/navigation.fxml"));
            Pane vBox = loader.load();
            vBox = (VBox) vBox.getChildren().get(0);
            HBox hBox1 = (HBox) vBox.getChildren().get(1);

            choiceBox = (ChoiceBox<String>)hBox1.getChildren().get(2);
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
            loader.setLocation(getClass().getResource("/Craftapp/title.fxml"));
            Pane pane = loader.load();
            pane = (Pane)pane.getChildren().get(0);
            titlePane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generate();
    }
    public void generate () {

        contentsView.getChildren().clear();
        HashMap <Beer, Integer> contents = shoppingCart.getContents();

        for (Beer beer : contents.keySet()) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Craftapp/cartItem.fxml"));
        HBox itemHolder;
            try {
                itemHolder = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
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

            removeButton.getStyleClass().add("remove-button");

            Button plusButton = (Button) buttons.getChildren().get(0);
            TextField countField = (TextField) buttons.getChildren().get(1);
            plusButton.setOnAction(event -> {
                shoppingCart.add(beer);
                countField.setText(String.valueOf(shoppingCart.getCount(beer)));
                generate();
            });
            countField.setText(String.valueOf(shoppingCart.getCount(beer)));

            countField.textProperty().addListener((observableValue, s, change) -> {

                if (change.isEmpty()) {
                    countField.setText("");
                    shoppingCart.remove(beer);

                } else {
                    try {
                        int count = Integer.parseInt(countField.getText());

                        if (!shoppingCart.contains(beer) && count > 0) {
                            shoppingCart.add(beer);
                        } else {
                            shoppingCart.setCount(beer, count);
                        }

                        countField.setText(change);

                    } catch ( Exception e) {
                        countField.setText(s);
                        System.out.println("Input must be a number.");
                    }

                }
            });

            Button minusButton = (Button) buttons.getChildren().get(2);
            minusButton.setOnAction(event -> {
                shoppingCart.decrease(beer);
                countField.setText(String.valueOf(shoppingCart.getCount(beer)));

                if (countField.getText().isEmpty() || countField.getText().equals("0")) {
                    shoppingCart.remove(beer);
                }
                generate();
            });

            itemHolder.getStyleClass().add("cartItem");
            contentsView.getChildren().add(itemHolder);
        }
        contentsView.setSpacing(10);
    }
}
