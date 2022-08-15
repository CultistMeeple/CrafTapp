package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerPorter extends Beer {

    public BeerPorter(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.Porter);
    }
}
