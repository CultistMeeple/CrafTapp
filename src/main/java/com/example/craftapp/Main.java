package com.example.craftapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static ArrayList <Beer> listOfBeers;
    public static ShoppingCart shoppingCart;
    @Override
    public void start(Stage stage) throws IOException {
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
        BreweryLatvia indieJanis = new BreweryLatvia("Indie Janis");

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
    }
}

