package Craftapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
public class ContentsController implements Initializable {
    private ShoppingCart shoppingCart;
    @FXML
    private ImageView beerImage;

    @FXML
    private TextField countField;

    @FXML
    private Button minusButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Button plusButton;

    @FXML
    private Label priceLabel;
    @FXML
    private Button removeButton;
    @FXML
    private HBox cartitem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCart = Main.shoppingCart;

    }
}