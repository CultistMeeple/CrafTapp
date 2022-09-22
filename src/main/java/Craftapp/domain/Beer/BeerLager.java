package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerLager extends Beer{

    public BeerLager(String name, Brewery brewery,double abv, double volume, double price) {
        super(name, brewery, abv,volume, price);
        setStyle(Style.Lager);
    }
}
