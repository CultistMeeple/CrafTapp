package Craftapp.util;

import Craftapp.domain.Beer.Beer;
import java.util.HashMap;

public class ShoppingCart {
    private double total;
    private HashMap<Beer, Integer> contents;

    public ShoppingCart () {
        total = 0.00;
        contents = new HashMap<>();
    }

    public void add(Beer beer) {

        if (contains(beer)) {
            int count = contents.get(beer);
            count++;
            contents.put(beer, count);
        } else {
            contents.put(beer, 1);
        }
    }

    public void decrease(Beer beer) {
        if (contains(beer)) {
            int count = contents.get(beer);
            count--;

            if (count < 1) {
                remove(beer);
            } else {
                contents.put(beer, count);
            }
        }
    }

    public void remove (Beer beer) {
        if (contains(beer)) {
            contents.remove(beer);
        }
    }

    public boolean contains (Beer beer) {
        return contents.containsKey(beer);
    }

    public double getTotal() {
        total = 0;
        for (Beer beer : contents.keySet()) {
            int count = contents.get(beer);
            double i = beer.getPrice() * count;
            total += i;
        }
        return total;
    }
    public HashMap<Beer, Integer> getContents() {
        return contents;
    }

    public void setCount (Beer beer, int count) {

        if (contains(beer)) {
            contents.put(beer, count);
        }
    }

    public int getCount(Beer beer) {
        if (contains(beer)) {
            return contents.get(beer);
        }
        return 0;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Beer beer : contents.keySet()) {
            sb.append(beer.getName()).append(", ");
        }
        return sb.toString();
    }
}
