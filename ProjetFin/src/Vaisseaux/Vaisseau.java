package Vaisseaux;

import Dechets.*;
import CentresDeTri.CentreDeTri;
import Planetes.Planete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public abstract class Vaisseau {
    private ArrayList<Dechet> dechets = new ArrayList<>();
    private int limiteDechets;
    private CentreDeTri emplacement;

    public Vaisseau(int limiteDechets) {

        this.limiteDechets = limiteDechets;

    }

    //recharge à partir de l'emplacement
    public  void charge(Stack<Dechet> dechets){

        this.dechets.clear();
        int size = dechets.size();
        for (int i = 0; i < size; i++) {

            if (this.dechets.size() < limiteDechets) {

                this.dechets.add(dechets.pop());

            } else {

                break;

            }
        }



    }

    public void charge (Planete planete) {

        for (int i = 0; i < limiteDechets; i++) {

            dechets.add(planete.charge());

        }

    }

    public void changerEmplacement (CentreDeTri nouvelEmplacement) {

        this.emplacement = nouvelEmplacement;

    }

    public void tri(){

        Collections.sort(dechets);

    }

    public void decharge(){

        tri();
        emplacement.dechargerVaisseau(this);

    }

    public ArrayList<Dechet> getDechets() {
        return dechets;
    }

    public CentreDeTri getEmplacement() {
        return emplacement;
    }

}
