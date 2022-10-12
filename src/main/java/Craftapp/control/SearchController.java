package Craftapp.control;

import Craftapp.domain.Beer.Beer;
import Craftapp.util.Searcher;
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
    private TextField searchBar;
    private Searcher<Beer> beerSearcher;
    private ArrayList <Beer> listOfBeers;
    private final String[] sort = {"Sort by name [a-z]", "Sort by name [z-a]", "Sort by price [low - high]", "Sort by price [high-low]"};
    private Sorter sorter;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;
        beerSearcher = Main.beerSearcher;
        listOfBeers = Main.listOfBeers;
        sorter = Main.sorter;

        //Sets up navigationPane, with searchBar and navigation buttons.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Craftapp/navigation.fxml"));
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

        //searchBar.setOnKeyTyped is here so that results automatically update and are shown for user.
        searchBar.setOnKeyTyped(keyEvent -> search());

        choiceBox.getItems().addAll(sort);
        choiceBox.setValue(sort[0]);

        choiceBox.setOnAction(event -> generateResults(beerSearcher.getResults()));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Craftapp/title.fxml"));
            Pane pane = loader.load();
            pane = (Pane)pane.getChildren().get(0);
            titlePane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generateResults(beerSearcher.getResults());

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
                    .append(beer.getStyle())
                    .append("\n").append(beerAbv).append("%")
                    .append("\n")
                    .append(beer.getBrewery());

            Label beerInfo = new Label(sb.toString());

            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.getChildren().addAll(image,beerInfo);
            hBox.getStyleClass().add("item");

            //Clears the StringBuilder
            sb.setLength(0);

            resultsView.setSpacing(5);
            resultsView.getChildren().add(hBox);
        }
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
