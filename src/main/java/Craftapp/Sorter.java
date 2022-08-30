package Craftapp;

import Craftapp.domain.Beer.Beer;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;

public class Sorter {
    private final Collator lvCollator;
    private String value;

    public Sorter() {
        try {
            lvCollator = new RuleBasedCollator(getLatvianRules());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ArrayList <Beer> sorted (ArrayList<Beer> list) {

        switch (value) {
            case "Sort by name [a-z]", "Sort by name [z-a]" -> sortByName(list);
            case "Sort by price [low - high]", "Sort by price [high-low]" -> sortByPrice(list);
        }
        return list;
    }

    private void sortByName (ArrayList<Beer> list) {

        //Switch does not allow array values to be used as expressions, thus strings are written in each case check.
        switch (getValue()) {
            // Sort alphabetically [a-z]
            case "Sort by name [a-z]"  ->list.sort((o1, o2) -> lvCollator.compare(o1.getName(), o2.getName()));

            // Sort alphabetically [z-a]
            case "Sort by name [z-a]" -> list.sort((o1, o2) -> -(lvCollator.compare(o1.getName(), o2.getName())));
        }
    }

    private void sortByPrice(ArrayList <Beer> list) {

        //Switch does not allow array values to be used as expressions, thus strings are written in each case check.
        switch (getValue()) {

            // Sort by price [low-high]
            case "Sort by price [low - high]" -> list.sort((o1, o2) -> {

                // Need to convert to Double, because compareTo() doesn't work with primitive type;
                Double price1 = o1.getPrice();
                Double price2 = o2.getPrice();

                //If the prices are the same, compares names instead;
                if (price1.equals(price2)) {
                    return lvCollator.compare(o1.getName(), o2.getName());
                }
                return price1.compareTo(price2);
            });

            // Sort by price [high-low]
            case "Sort by price [high-low]" -> list.sort((o1, o2) -> {
                // Need to convert to Double, because compareTo() doesn't work with primitive type;
                Double price1 = o1.getPrice();
                Double price2 = o2.getPrice();

                //If the prices are the same, compares names instead;
                if (price1.equals(price2)) {

                    return lvCollator.compare(o1.getName(), o2.getName());
                }
                return -(price1.compareTo(price2));
            });
        }
    }

    private String getLatvianRules() {

        //Had issues sorting, because default Collator had some rules wrong, like, completely missing ā,Ā,
        //Comparator was putting š,Š last;
        //Introduced RuleBasedCollator with my own set of rules;

        //https://docs.oracle.com/javase/tutorial/i18n/text/rule.html
        //https://docs.oracle.com/javase/9/docs/api/java/text/RuleBasedCollator.html

        String latvianRules = ("< a,A < ā,Ā < b,B < c,C < č,Č < d,D " +
                "< e,E < ē,Ē < f,F < g,G < ģ,Ģ < h,H < i,I < ī,Ī < j,J " +
                "< k,K < ķ,Ķ < l,L < ļ,Ļ < m,M < n,N < ņ,Ņ < o,O < p,P " +
                "< q,Q < r,R < s,S < š,Š < t,T < u,U < ū,Ū < v,V < w,W " +
                "< x,X < y,Y < z,Z < ž,Ž");

        return latvianRules;
    }
}
