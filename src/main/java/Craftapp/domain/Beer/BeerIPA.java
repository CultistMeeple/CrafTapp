package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerIPA extends Beer {

    public BeerIPA(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(TypesOfBeer.IPA);
    }

}
