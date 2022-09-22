package Craftapp;

import Craftapp.domain.Beer.*;
import Craftapp.domain.Brewery.BreweryLatvia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    protected static ArrayList <Beer> listOfBeers;
    protected static Searcher <Beer> beerSearcher;
    protected static ShoppingCart shoppingCart;
    public static SceneSwitcher switcher;
    public static Sorter sorter;
    private int width;
    private int height;

    @Override
    public void start(Stage stage) throws IOException {

        switcher.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("default.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Hopp");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {

        width = 335;
        height = 600;
        switcher = new SceneSwitcher(width, height);
        listOfBeers = new ArrayList<>();
        shoppingCart = new ShoppingCart();
        beerSearcher = new Searcher<>();
        sorter = new Sorter();
        addBeers(listOfBeers);

        super.init();
    }


    public static void main(String[] args) {
        launch();
    }


    public static void addBeers(ArrayList<Beer> list) {

        BreweryLatvia malduguns = new BreweryLatvia("Malduguns");
        BreweryLatvia odu = new BreweryLatvia("Odu");
        BreweryLatvia viedi = new BreweryLatvia("Viedi");
        BreweryLatvia hopalaa = new BreweryLatvia("Hopalaa");
        BreweryLatvia indieJanis = new BreweryLatvia("Indie Jānis");
        BreweryLatvia duna = new BreweryLatvia("Duna");

        BeerIPA sanslide = new BeerIPA("Sānslīde", malduguns, 6.8, 0.5, 3.62);
        sanslide.setImage("sanslide.jpg");
        list.add(sanslide);

        BeerPorter pilotaNakts = new BeerPorter("Pilota Nakts", malduguns,7.0, 0.5, 3.62);
        pilotaNakts.setImage("pilota nakts.jpg");
        list.add(pilotaNakts);

        BeerStout coconutDream = new BeerStout("Coconut Dream", odu, 10, 0.33, 6.23);
        coconutDream.setImage("coconut dream.jpg");
        list.add(coconutDream);

        BeerAle sazobe = new BeerAle("Sazobe", viedi,5.8, 0.5, 3.45);
        sazobe.setImage("sazobe.png");
        list.add(sazobe);

        BeerIPA tropical  = new BeerIPA("Tropical", hopalaa, 7.3, 0.44, 3.86);
        tropical.setImage("tropical.jpg");
        list.add(tropical);

        BeerIPA dank  = new BeerIPA("Dank", hopalaa,7, 0.44, 4.35);
        dank.setImage("dank.jpg");
        list.add(dank);

        BeerIPA oldSchool = new BeerIPA("Old School", indieJanis,5.5, 0.5, 3.25);
        oldSchool.setImage("old school.jpg");
        list.add(oldSchool);

        BeerIPA napoleons = new BeerIPA("Napoleons", indieJanis, 5, 0.5, 3.05);
        napoleons.setImage("napoleons.jpg");
        list.add(napoleons);

        BeerIPA rodeo = new BeerIPA("Rodeo", viedi,5.6, 0.5,3.56);
        rodeo.setImage("rodeo.png");
        list.add(rodeo);

        BeerStout tumsaPuse = new BeerStout("Tumšā Puse", malduguns, 11.2, 0.33, 3.52);
        tumsaPuse.setImage("tumsa puse.jpg");
        list.add(tumsaPuse);

        BeerLager svika = new BeerLager("Švīka", malduguns, 5.2, 0.5, 3.16);
        svika.setImage("svika.jpg");
        list.add(svika);

        BeerSour mangoSour = new BeerSour("Mango Sour", duna, 3.8, 0.44, 4.90);
        mangoSour.setImage("mango sour.jpg");
        list.add(mangoSour);

        BeerSour peachSour = new BeerSour("Peach Sour", duna, 3.8,0.44, 4.90);
        peachSour.setImage("peach sour.jpg");
        list.add(peachSour);

        BeerAle juicyPaleAle = new BeerAle("Juicy Pale Ale", duna,4.5, 0.44, 4.20);
        juicyPaleAle.setImage("juicy pale ale.jpg");
        list.add(juicyPaleAle);
    }

}

