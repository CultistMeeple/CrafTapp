package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerSour extends Beer{
    public BeerSour(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.Sour);
    }
}
