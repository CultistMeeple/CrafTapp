package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;
import javafx.scene.image.Image;

public abstract class Beer{
    private String name;
    private final Brewery brewery;
    private Type type;
    private double price;
    private boolean isOnSale;
    private double discount;
    private Image image;

    public Beer (String name, Brewery brewery, double price) {
        this.name = name;
        this.brewery = brewery;
        this.price = price;
    }

    public void setType(Type type) {
        this.type = type;
        isOnSale = false;
        discount = 0;
    }

    public String getType() {
        return String.valueOf(type);
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setDiscount(double discount) {
        if (discount == 0) {
            isOnSale = false;
        } else {
            isOnSale = true;
            this.discount = discount;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = new Image("file:src/main/resources/img/beer/" +image);
    }

    public Image getImage() {
        return image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getName();
    }
}


