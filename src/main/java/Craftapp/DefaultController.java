package Craftapp;

import Craftapp.domain.Beer.Beer;
import Craftapp.domain.Beer.TypesOfBeer;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.RuleBasedCollator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML
    private ChoiceBox <String>choiceBox;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private ShoppingCart shoppingCart;
    private ArrayList<Beer> listOfBeers = Main.listOfBeers;
    private ArrayList <CheckBox> listOfCheckBoxes;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private RuleBasedCollator lvCollator;
    @FXML
    private Pane navigationPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvCollator = Main.lvCollator;
        shoppingCart = Main.shoppingCart;
        listOfCheckBoxes = new ArrayList<>();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("navigation.fxml"));
            Pane parent = loader.load();
            parent = (Pane)parent.getChildren().get(0);
            searchButton = (Button)parent.getChildren().get(0);
            addButton = (Button)parent.getChildren().get(1);
            homeButton = (Button)parent.getChildren().get(2);
            choiceBox = (ChoiceBox<String>)parent.getChildren().get(3);
            searchBar = (TextField) parent.getChildren().get(4);
            navigationPane.getChildren().addAll(searchButton, addButton, homeButton, choiceBox, searchBar);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        searchBar.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    search();
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

        if (!input.isEmpty()){
            ArrayList <Beer> results = new ArrayList<>();

            String first = String.valueOf(input.charAt(0));
            String regexFirst = "(" + first.toUpperCase() + "|" + first.toLowerCase() + ")";
            String rest = input.replaceFirst(regexFirst, "");
            String regex = regexFirst + "(" + rest + ")";

            Pattern pattern = Pattern.compile(regex);

            for (Beer beer : listOfBeers) {

                Matcher matcher = pattern.matcher(beer.getName());
                boolean match = matcher.find();

                if (match) {
                    results.add(beer);
                }
            }
            generateBeer(results);

        } else {
            filterCheckBoxChoice();
        }
    }

    //Populates the right side of SplitPane with Beer class objects and adds functionality from ItemController;
        private void generateBeer(ArrayList <Beer> list) {

            //Resets the market.
            tilePane.getChildren().clear();

            // needed here, to avoid Pane stretching too much or too little
            if (list.size()%2 != 0) {
                //Checks, if the number is odd, otherwise, with odd numbers there are not enough rows;
                tilePane.setPrefRows(list.size()/2 +1);
            } else {
                tilePane.setPrefRows(list.size() / 2);
            }

            if (list.size()%2 != 0) {
                //Checks, if the number is odd, otherwise, with odd numbers the pane is too short;
                tileAnchor.setPrefHeight((list.size() / 2 +1) *160);
            } else {
                tileAnchor.setPrefHeight((list.size() / 2) *160);
            }

            //Sorts beer using value of choiceBox;

            switch (choiceBox.getValue()) {
                case "Sort by name [a-z]", "Sort by name [z-a]" -> sortBeerByName(list);
                case "Sort by price [low - high]", "Sort by price [high-low]" -> sortBeerByPrice(list);
            }

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

                String toPrint = String.format("%.2f",list.get(i).getPrice());
                buyButton.setText("€ " +toPrint);
                beerImage.setImage(list.get(i).getImage());

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

                itemPane = new Pane();

                itemPane.getChildren().addAll(beerImage,beerName, buyButton);
                itemPane.setId("pane" +i);

                tilePane.getChildren().add(itemPane);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);}
    }
    public void sortBeerByName (ArrayList <Beer> list) {

        //Switch does not allow array values to be used as expressions, thus strings are written in each case check.
        switch (choiceBox.getValue()) {
            // Sort alphabetically [a-z]
            case "Sort by name [a-z]"  ->list.sort(new Comparator<Beer>() {
                @Override
                public int compare(Beer o1, Beer o2) {
                    return lvCollator.compare(o1.getName(), o2.getName());
                }
            });

            // Sort alphabetically [z-a]
            case "Sort by name [z-a]" -> list.sort(new Comparator<Beer>() {
                    @Override
                    public int compare(Beer o1, Beer o2) {
                        return -(lvCollator.compare(o1.getName(), o2.getName()));
                    }
                });
        }
    }

    public void sortBeerByPrice(ArrayList <Beer> list) {

        //Switch does not allow array values to be used as expressions, thus strings are written in each case check.
        switch (choiceBox.getValue()) {

            // Sort by price [low-high]
            case "Sort by price [low - high]" -> list.sort(new Comparator<Beer>() {
                @Override
                public int compare(Beer o1, Beer o2) {

                    // Need to convert to Double, because compareTo() doesn't work with primitive type;
                    Double price1 = o1.getPrice();
                    Double price2 = o2.getPrice();

                    //If the prices are the same, compares names instead;
                    if (price1.equals(price2)) {
                        return lvCollator.compare(o1.getName(), o2.getName());
                    }
                    return price1.compareTo(price2);
                }
            });

            // Sort by price [high-low]
                case "Sort by price [high-low]" -> list.sort(new Comparator<Beer>() {
                    @Override
                    public int compare(Beer o1, Beer o2) {
                        // Need to convert to Double, because compareTo() doesn't work with primitive type;
                        Double price1 = o1.getPrice();
                        Double price2 = o2.getPrice();

                        //If the prices are the same, compares names instead;
                        if (price1.equals(price2)) {

                            return lvCollator.compare(o1.getName(), o2.getName());
                        }
                        return -(price1.compareTo(price2));
                    }
                });
        }
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
                    filterCheckBoxChoice();
                }
            });
            checkBox.setText(String.valueOf(arrayOfBeer[i]));

            leftPane.getChildren().add(checkBox);
            listOfCheckBoxes.add(checkBox);
        }
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
            cartButton.setStyle("-fx-background-color:#facb89");
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



