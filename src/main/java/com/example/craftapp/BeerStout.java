package com.example.craftapp;

public class BeerStout extends Beer{

    public BeerStout(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.Stout);
    }
}
