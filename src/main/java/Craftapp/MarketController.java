package Craftapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    private ShoppingCart shoppingCart;
    @FXML
    private Button buyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoppingCart = Main.shoppingCart;

    }
}