package Craftapp.domain;

import Craftapp.domain.Beer.Beer;

import java.util.HashMap;

public class Shop {

    private String name;
    private String country;
    private String city;
    private String address;
    private String website;
    private HashMap <String, Beer>inventory;

    public Shop () {
        inventory = new HashMap<>();
    }
}
