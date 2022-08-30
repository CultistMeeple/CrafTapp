package Craftapp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Searcher <T> {
    private ArrayList<T> results;
    private String phrase;

    public Searcher() {
        results = new ArrayList<>();
        phrase = "";
    }

    public void add(T object) {
        results.add(object);
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void clear() {
        results.clear();
    }

    public void searchList(ArrayList <T> list) {

        if (!phrase.isEmpty()) {
            String first = String.valueOf(phrase.charAt(0));
            String regexFirst = "(" + first.toUpperCase() + "|" + first.toLowerCase() + ")";
            String rest = phrase.replaceFirst(regexFirst, "");

            StringBuilder sb = new StringBuilder();
            sb.append(regexFirst);

            for (int i = 0; i < rest.length(); i++) {
                String letter = String.valueOf(rest.charAt(i));
                sb.append("(").append(letter.toLowerCase()).append("|").append(letter.toUpperCase()).append(")");
            }

            String regex = sb.toString();
            //System.out.println("Regex: " +regex);
            Pattern pattern = Pattern.compile(regex);

            for (T object : list) {

                Matcher matcher = pattern.matcher(object.toString());
                boolean match = matcher.find();

                if (match) {
                    add(object);
                }
            }
        }
    }
}

