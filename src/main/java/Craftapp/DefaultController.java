package Craftapp;

import Craftapp.domain.Beer.Beer;
import Craftapp.domain.Beer.Type;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private ChoiceBox <String>choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private ShoppingCart shoppingCart;
    private final ArrayList<Beer> listOfBeers = Main.listOfBeers;
    private ArrayList <CheckBox> listOfCheckBoxes;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane navigationPane;
    private Searcher <Beer> beerSearcher;
    private Sorter sorter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sorter = Main.sorter;
        shoppingCart = Main.shoppingCart;
        listOfCheckBoxes = new ArrayList<>();
        beerSearcher = Main.beerSearcher;

        //Sets up navigationPane, with searchBar and navigation buttons.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("navigation.fxml"));
            Pane vBox = loader.load();
            vBox = (VBox) vBox.getChildren().get(0);
            HBox hBox = (HBox) vBox.getChildren().get(0);
            HBox hBox1 = (HBox) vBox.getChildren().get(1);

            addButton = (Button)hBox1.getChildren().get(1);
            homeButton = (Button)hBox1.getChildren().get(0);
            choiceBox = (ChoiceBox<String>)hBox1.getChildren().get(2);
            searchButton = (Button)hBox.getChildren().get(1);
            searchBar = (TextField) hBox.getChildren().get(0);
            navigationPane.getChildren().add(vBox);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        //Shows the previous search.
        searchBar.setText(beerSearcher.getPhrase());

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

        choiceBox.getItems().addAll(sort);
        choiceBox.setValue(sort[0]);
        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // filterCheckBoxChoice() already contains generateBeer() method;
                //generateBeer() contains sortBeer();
                filterCheckBoxChoice();
            }
        });

        generateCheckBoxes();

        //Populates the market "window" with products.
        generateBeer(listOfBeers);

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

    private void search() {
        String input = searchBar.getText();
        input = input.trim();
        beerSearcher.clear();
        beerSearcher.setPhrase(input);
        beerSearcher.searchList(listOfBeers);
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

            if (list.size()%2 != 0) {
                //Checks, if the number is odd, otherwise, with odd numbers the pane is too short;
                tileAnchor.setPrefHeight((double)(list.size() / 2 +1) *160);
            } else {
                tileAnchor.setPrefHeight((double)(list.size() / 2) *160);
            }

            //Sorts beer using value of choiceBox;
            sorter.setValue(choiceBox.getValue());
            sorter.sorted(list);

            Pane itemHolder;
            try {

            for (int i = 0; i < list.size(); i++) {
                FXMLLoader itemLoader = new FXMLLoader();
                itemLoader.setLocation(getClass().getResource("item.fxml"));
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

                buyButton.setId("buy" +i);
                buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            addToShoppingCart(buyButton.getId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }                    }
                });

                itemHolder.setId("pane" +i);

                //Setting background color, so that itemHolder appears monolith and casts shadow.
                itemHolder.setStyle("-fx-background-color:white");
                marketPane.getChildren().add(itemHolder);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);}
    }
    public void generateCheckBoxes() {

        double prefWidth = 80;
        double prefHeight = 20;

        Type[] arrayOfBeer = Type.values();

        for (Type type : arrayOfBeer) {

            CheckBox checkBox = new CheckBox();
            checkBox.setPrefWidth(prefWidth);
            checkBox.setPrefHeight(prefHeight);
            checkBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    filterCheckBoxChoice();
                }
            });
            checkBox.setText(String.valueOf(type));

            listOfCheckBoxes.add(checkBox);
        }
        //So that the elements are not on top of each other.
        checkVBox.setSpacing(5);
        checkVBox.getChildren().addAll(listOfCheckBoxes);
    }
    void filterCheckBoxChoice() {

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

    public void addToShoppingCart(String id) throws IOException {

        //Gets buyButton id, removes "buy" substring, so that only an int is remaining, then it is passed as index to listOfBeers.
        String replace = id.substring(0, 3);
        id = id.replace(replace, "");
        int index = Integer.parseInt(id);

        shoppingCart.add(listOfBeers.get(index));
        setCartButtonText();
    }

    public void setCartButtonText() {

        double total = shoppingCart.getTotal();
        String toPrint = String.format("%.2f",total);

        cartButton.setText("€ " +toPrint);

        if (shoppingCart.getTotal() > 0) {
            cartButton.setStyle("-fx-background-color:#66FF66");
        }
    }

    public void switchToResults (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("results.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
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
}



