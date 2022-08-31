package Craftapp;

import Craftapp.domain.Beer.Beer;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private Pane navigationPane;
    @FXML
    private Pane titlePane;
    @FXML
    private VBox resultsView;
    private ChoiceBox <String> choiceBox;
    private ShoppingCart shoppingCart;
    private Button cartButton;
    private Button searchButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private TextField searchBar;
    private Searcher <Beer> beerSearcher;
    private ArrayList <Beer> listOfBeers;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private Sorter sorter;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;
        beerSearcher = Main.beerSearcher;
        listOfBeers = Main.listOfBeers;
        sorter = Main.sorter;

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

        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generateResults(beerSearcher.getResults());
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

        generateResults(beerSearcher.getResults());

        searchBar.setText(beerSearcher.getPhrase());
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
            }
        });
    }

    public void generateResults(ArrayList<Beer> list) {

        sorter.setValue(choiceBox.getValue());
        list = sorter.sorted(list);

        // Must clear children before new are added, otherwise sorting isn't happening.
        resultsView.getChildren().clear();

        Label info = new Label("Displaying " +list.size() +" matching results.");
        info.setStyle("-fx-text-fill:#04c411; -fx-font-weight:bold;");

        resultsView.getChildren().add(info);

        StringBuilder sb = new StringBuilder();

        for (Beer beer : list) {

            ImageView image = new ImageView(beer.getImage());
            image.setFitHeight(100);
            image.setFitWidth(100);

            String beerPrice = String.format("%.2f", beer.getPrice());
            String beerAbv = String.format("%.1f", beer.getAbv());
            beerPrice = "€ "+ beerPrice;
            sb.append(beer.getName())
                    .append("\n")
                    .append(beerPrice)
                    .append("\n")
                    .append(beer.getType())
                    .append("\n").append(beerAbv).append("%")
                    .append("\n")
                    .append(beer.getBrewery());

            Label beerInfo = new Label(sb.toString());

            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.getChildren().addAll(image,beerInfo);

            //Clears the StringBuilder
            sb.setLength(0);

            resultsView.setSpacing(5);
            resultsView.getChildren().add(hBox);
        }
    }


    public void setCartButtonText() {

        double total = shoppingCart.getTotal();
        String toPrint = String.format("%.2f",total);

        cartButton.setText("€ " +toPrint);

        if (shoppingCart.getTotal() > 0) {
            cartButton.setStyle("-fx-background-color:#66FF66");
        }
    }

    public void switchToHome (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("default.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 335,600);
        stage.setScene(scene);
        stage.show();
    }

    private void search() {
        String input = searchBar.getText();
        input = input.trim();
        //Clear to reset the internal list.
        beerSearcher.clear();
        beerSearcher.setPhrase(input);
        beerSearcher.searchList(listOfBeers);
        generateResults(beerSearcher.getResults());
    }
}
