package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerAle extends Beer {

    public BeerAle(String name, Brewery brewery,double abv, double volume, double price) {
        super(name, brewery, abv, volume, price);
        setType(Type.Ale);
    }
}
