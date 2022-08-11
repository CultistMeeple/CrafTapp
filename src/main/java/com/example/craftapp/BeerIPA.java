package com.example.craftapp;

public class BeerIPA extends Beer{

    public BeerIPA(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.IPA);
    }
}
