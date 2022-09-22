package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerIPA extends Beer {

    public BeerIPA(String name, Brewery brewery, double abv, double volume, double price) {
        super(name, brewery, abv, volume, price);
        setStyle(Style.IPA);
    }

}
