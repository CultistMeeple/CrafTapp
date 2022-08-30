package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;

public class BeerLager extends Beer{

    public BeerLager(String name, Brewery brewery, double price) {
        super(name, brewery, price);
        setType(Type.Lager);
    }
}
