package Craftapp.control;

import Craftapp.domain.Beer.Beer;
import Craftapp.domain.Beer.Style;
import Craftapp.util.ShoppingCart;
import Craftapp.util.Sorter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DefaultController implements Initializable {
    @FXML
    private TilePane marketPane;
    @FXML
    private VBox checkVBox;
    @FXML
    private AnchorPane tileAnchor;
    @FXML
    private ImageView beerImage;
    @FXML
    private Label beerName;
    @FXML
    private Pane titlePane;
    @FXML
    private ChoiceBox <String> choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private ShoppingCart shoppingCart;
    private final ArrayList<Beer> listOfBeers = Main.listOfBeers;
    private ArrayList <Beer> filteredList;
    private ArrayList <CheckBox> listOfCheckBoxes;
    @FXML
    private Pane navigationPane;
    private Sorter sorter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sorter = Main.sorter;
        shoppingCart = Main.shoppingCart;
        listOfCheckBoxes = new ArrayList<>();
        filteredList = new ArrayList<>();

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
        choiceBox.setOnAction(event -> {
            // filterCheckBoxChoice() already contains generateBeer() method;
            //generateBeer() contains sortBeer();
            filterCheckBoxChoice();
        });

        generateCheckBoxes();
        //Populates the market "window" with products.
        generateBeer(listOfBeers);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Craftapp/title.fxml"));
            Pane pane = loader.load();
            pane = (Pane)pane.getChildren().get(0);
            titlePane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Populates the right side of SplitPane with Beer class objects and adds functionality from ItemController;
        private void generateBeer(ArrayList <Beer> list) {

            //Resets the market.
            marketPane.getChildren().clear();

            // needed here, to avoid Pane stretching too much or too little
            if (list.size()%2 != 0) {
                //Checks, if the number is odd, otherwise, with odd numbers there are not enough rows;
                marketPane.setPrefRows(list.size()/2 +1);
            } else {
                marketPane.setPrefRows(list.size() / 2);
            }

            //Sorts beer using value of choiceBox;
            sorter.setValue(choiceBox.getValue());
            sorter.sorted(list);

            Pane itemHolder;
            try {

            for (int i = 0; i < list.size(); i++) {
                FXMLLoader itemLoader = new FXMLLoader();
                itemLoader.setLocation(getClass().getResource("/Craftapp/item.fxml"));
                itemHolder = itemLoader.load();
                Pane itemPane = (Pane) itemHolder.getChildren().get(0);
                beerImage = (ImageView) itemPane.getChildren().get(0);

                VBox vBox = (VBox) itemHolder.getChildren().get(1);
                beerName = (Label) vBox.getChildren().get(0);
                Button buyButton = (Button) vBox.getChildren().get(1);

                beerName.setText(list.get(i).getName());
                beerImage.setImage(list.get(i).getImage());

                String toPrint = String.format("%.2f",list.get(i).getPrice());
                buyButton.setText("€ " +toPrint);

                buyButton.setId("buyButton");

                int iFin = i;
                buyButton.setOnMouseClicked(mouseEvent -> shoppingCart.add(list.get(iFin)));

                itemHolder.getStyleClass().add("item");
                marketPane.getChildren().add(itemHolder);

                double vSpacing = marketPane.getVgap();
                double totalVSpacing = vSpacing * list.size()/2;
                double prefHeight = itemHolder.getPrefHeight();

                if (list.size()%2 != 0) {
                    //Checks, if the number is odd, otherwise, with odd numbers the pane is too short;
                    tileAnchor.setPrefHeight((double)(list.size() / 2 +1) *prefHeight + totalVSpacing +vSpacing);
                } else {
                    tileAnchor.setPrefHeight((double)(list.size() / 2) *prefHeight + totalVSpacing +vSpacing);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);}
    }
    public void generateCheckBoxes() {

        double prefWidth = 80;
        double prefHeight = 20;

        Style[] arrayOfBeer = Style.values();

        for (Style type : arrayOfBeer) {

            CheckBox checkBox = new CheckBox();
            checkBox.setPrefWidth(prefWidth);
            checkBox.setPrefHeight(prefHeight);
            checkBox.setOnAction(event -> filterCheckBoxChoice());
            checkBox.setText(String.valueOf(type));

            listOfCheckBoxes.add(checkBox);
        }
        //So that the elements are not on top of each other.
        checkVBox.setSpacing(5);
        checkVBox.getChildren().addAll(listOfCheckBoxes);
    }
    void filterCheckBoxChoice() {
        //Used to keep track of checked checkBoxes;
        boolean isChecked = false;
        //Resets contents filteredList;
        filteredList.clear();

        for (CheckBox checkBox : listOfCheckBoxes) {
                    for (Beer beer : listOfBeers) {

                        if (checkBox.isSelected()) {
                            isChecked = true;
                            if (!filteredList.contains(beer) && checkBox.getText().equals(beer.getStyle())) {
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
}