package Craftapp;
import Craftapp.domain.Beer.Beer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox<?> choiceBox;
    @FXML
    private Button homeButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Button cartButton;
    @FXML
    private ImageView cartIcon;
    @FXML
    private ImageView hopsIcon;
    @FXML
    private Button profileButton;
    @FXML
    private ImageView profileIcon;
    @FXML
    private Label title;
    @FXML
    private Pane titlePane;
    private SceneSwitcher switcher;
    private ArrayList<Beer> listOfBeers;
    private ShoppingCart shoppingCart;
    private Searcher <Beer> beerSearcher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switcher = Main.switcher;
        listOfBeers = Main.listOfBeers;
        shoppingCart = Main.shoppingCart;
        beerSearcher = Main.beerSearcher;
    }

    @FXML
    void switchToHome() {
        switcher.switchScene("home");
    }
    @FXML
    void switchToResults() {
        //search() method already prepares the results ArrayList in Searcher object before switching;
        search();
        switcher.switchScene("results");
    }
    @FXML
    void switchToAdd () {
        switcher.switchScene("add");
    }
    @FXML
    void switchToCart(){
        switcher.switchScene("cart");
    }

    @FXML
    private void search() {
        String input = searchBar.getText();
        input = input.trim();
        //Clear to reset the internal list.
        beerSearcher.clear();
        beerSearcher.setPhrase(input);
        beerSearcher.searchList(listOfBeers);
    }
}