package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerStout extends Beer {

    public BeerStout(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(Type.Stout);
    }
}
