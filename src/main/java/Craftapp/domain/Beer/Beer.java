package Craftapp.domain.Beer;

import Craftapp.domain.Brewery.Brewery;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Beer{
    private String name;
    private final Brewery brewery;
    private Style style;
    private double price;
    private double abv;
    private double volume;
    private Image image;

    public Beer (String name, Brewery brewery, double abv, double volume, double price) {
        this.name = name;
        this.brewery = brewery;
        this.abv = abv;
        this.volume = volume;
        this.price = price;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getStyle() {
        return String.valueOf(style);
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = new Image("file:src/main/resources/img/beer/" +image);
    }

    public Image getImage() {
        return image;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getAbv() {
        return abv;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beer beer)) return false;
        return name.equals(beer.name) && Objects.equals(brewery, beer.brewery) && style == beer.style;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brewery);
    }

    @Override
    public String toString() {
        return getName();
    }
}


