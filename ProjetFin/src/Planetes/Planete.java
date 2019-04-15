package Planetes;

import Dechets.*;

public abstract class Planete {

    private float[] chancesDechets; //0 = plutonium 1 = thulium 2 = gadolium 3 = terbium 4 = neptunium

    public Planete (float[] chancesDechets) {
        chancesDechets = chancesDechets;
    }

    public Dechet charge(){

        //on roule un dé pour savoir quel type de déchet sera récolté par le vaisseau
        double roll = Math.random();

        if (roll <= chancesDechets[0]) {

            return new Plutonium();

        } else if (roll < chancesDechets[1]) {

            return new Thulium();

        } else if (roll < chancesDechets[2]) {

            return new Gadolinium();

        } else if (roll < chancesDechets[3]) {

            return new Terbium();

        } else if (roll < chancesDechets[4]) {

            return new Neptunium();

        } else {

            return null;

        }

    }

    public float[] getChancesDechets() {
        return chancesDechets;
    }

}
