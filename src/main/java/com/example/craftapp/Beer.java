package com.example.craftapp;

import javafx.scene.image.Image;

abstract class Beer {
    private String name;
    private Brewery brewery;
    private TypesOfBeer type;
    private double price;
    private boolean isOnSale;
    private double discount;
    private Image image;

    public Beer (String name, Brewery brewery, double price) {
        this.name = name;
        this.brewery = brewery;
        this.price = price;
    }

    public void setType(TypesOfBeer type) {
        this.type = type;
        isOnSale = false;
        discount = 0;
    }

    public void setDiscount(double discount) {
        if (discount == 0) {
            isOnSale = false;
        } else {
            isOnSale = true;
            this.discount = discount;
        }
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
}
