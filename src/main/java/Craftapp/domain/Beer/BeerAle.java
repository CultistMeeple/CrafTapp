package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerAle extends Beer {

    public BeerAle(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(Type.Ale);
    }
}
