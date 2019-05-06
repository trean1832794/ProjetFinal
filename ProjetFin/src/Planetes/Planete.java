package Planetes;

import Dechets.*;
import Exceptions.WaitingFullException;
import Simulation.Main;

public class Planete {


    private float[] chancesDechets; //0 = plutonium 1 = thulium 2 = gadolium 3 = terbium 4 = neptunium

    public Planete (float[] chancesDechets) {
        this.chancesDechets = chancesDechets;
    }

    public Dechet charge(){

        //on roule un dé pour savoir quel type de déchet sera récolté par le vaisseau
        double roll = Math.random();
        float chance = 0.0f;
        for (Dechet dechet : Main.dechets) {

            chance = chancesDechets[dechet.getId()];
            if (roll <= chance) {

                //on return le dechet
                return dechet;

            }

        }

        System.out.println("Aucun déchet n'a été pogné: checkez vos valeurs dans le fichier xml");
        return null;

    }

    public float[] getChancesDechets() {
        return chancesDechets;
    }

}
