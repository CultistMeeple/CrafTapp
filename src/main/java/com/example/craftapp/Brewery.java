package com.example.craftapp;

import java.util.ArrayList;
import java.util.HashMap;
public abstract class Brewery {

    private String name;
    private Countries country;
    private String city;
    private String address;
    private String website;
    private ArrayList <BreweryLatvia> latvianBreweries;
    private HashMap<String, Beer> listOfBeers;

    public Brewery (String name){
        listOfBeers = new HashMap<>();
        latvianBreweries = new ArrayList<>();
    }

    public void setCountry(Countries country) {
        this.country = country;
    }
}
