package com.example.craftapp;

import java.util.ArrayList;

public class ShoppingCart {
    private double total;
    private ArrayList <Beer> contents;

    public ShoppingCart () {
        total = 0.00;
        contents = new ArrayList<>();
    }

    public void add(Beer beer) {
        contents.add(beer);
        total += beer.getPrice();
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {

        String beers  ="";
        for (Beer beer : contents) {
            beers += beer +"\n";
        }
        return beers +total;
    }
}
