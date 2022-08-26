package Craftapp;

import Craftapp.domain.Beer.*;
import Craftapp.domain.Brewery.BreweryLatvia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;

public class Main extends Application {
    public static ArrayList <Beer> listOfBeers;
    public static ShoppingCart shoppingCart;
    public static RuleBasedCollator lvCollator;
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("default.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
        stage.setTitle("CrafTapp");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {

        listOfBeers = new ArrayList<>();
        shoppingCart = new ShoppingCart();
        addBeers(listOfBeers);

        try {
            lvCollator = new RuleBasedCollator(getLatvianRules());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        super.init();
    }


    public static void main(String[] args) {
        launch();
    }

    public String getLatvianRules() {

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
    public static void addBeers(ArrayList<Beer> list) {

        BreweryLatvia malduguns = new BreweryLatvia("Malduguns");
        BreweryLatvia odu = new BreweryLatvia("Odu");
        BreweryLatvia viedi = new BreweryLatvia("Viedi");
        BreweryLatvia hopalaa = new BreweryLatvia("Hopalaa");
        BreweryLatvia indieJanis = new BreweryLatvia("Indie Jānis");
        BreweryLatvia duna = new BreweryLatvia("Duna");

        BeerIPA sanslide = new BeerIPA("Sānslīde", malduguns, 3.62);
        sanslide.setImage("sanslide.jpg");
        list.add(sanslide);

        BeerPorter pilotaNakts = new BeerPorter("Pilota Nakts", malduguns, 3.62);
        pilotaNakts.setImage("pilota nakts.jpg");
        list.add(pilotaNakts);

        BeerStout coconutDream = new BeerStout("Coconut Dream", odu, 6.23);
        coconutDream.setImage("coconut dream.jpg");
        list.add(coconutDream);

        BeerAle sazobe = new BeerAle("Sazobe", viedi, 3.45);
        sazobe.setImage("sazobe.png");
        list.add(sazobe);

        BeerIPA tropical  = new BeerIPA("Tropical", hopalaa, 3.86);
        tropical.setImage("tropical.jpg");
        list.add(tropical);

        BeerIPA dank  = new BeerIPA("Dank", hopalaa, 4.35);
        dank.setImage("dank.jpg");
        list.add(dank);

        BeerIPA oldSchool = new BeerIPA("Old School", indieJanis, 3.25);
        oldSchool.setImage("old school.jpg");
        list.add(oldSchool);

        BeerIPA napoleons = new BeerIPA("Napoleons", indieJanis, 3.05);
        napoleons.setImage("napoleons.jpg");
        list.add(napoleons);

        BeerIPA rodeo = new BeerIPA("Rodeo", viedi,3.56);
        rodeo.setImage("rodeo.png");
        list.add(rodeo);

        BeerStout tumsaPuse = new BeerStout("Tumšā Puse", malduguns,3.52);
        tumsaPuse.setImage("tumsa puse.jpg");
        list.add(tumsaPuse);

        BeerLager svika = new BeerLager("Švīka", malduguns, 3.16);
        svika.setImage("svika.jpg");
        list.add(svika);

        BeerSour mangoSour = new BeerSour("Mango Sour", duna, 4.90);
        mangoSour.setImage("mango sour.jpg");
        list.add(mangoSour);

        BeerSour peachSour = new BeerSour("Peach Sour", duna, 4.90);
        peachSour.setImage("peach sour.jpg");
        list.add(peachSour);

        BeerAle juicyPaleAle = new BeerAle("Juicy Pale Ale", duna, 4.20);
        juicyPaleAle.setImage("juicy pale ale.jpg");
        list.add(juicyPaleAle);
    }

}

