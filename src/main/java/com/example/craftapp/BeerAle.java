package com.example.craftapp;

public class BeerAle extends Beer {

    public BeerAle(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.Ale);
    }
}
