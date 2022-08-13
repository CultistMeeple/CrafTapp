package com.example.craftapp;

public class BeerPorter extends Beer {

    public BeerPorter(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.Porter);
    }
}
