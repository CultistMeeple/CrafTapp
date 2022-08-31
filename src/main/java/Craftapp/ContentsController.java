package Craftapp;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
public class ContentsController implements Initializable {
    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCart = Main.shoppingCart;

    }
}