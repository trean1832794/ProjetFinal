package Vaisseaux;

import Dechets.*;
import CentresDeTri.CentreDeTri;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Vaisseau {
    private ArrayList<Dechet> dechets = new ArrayList<>();
    private int limiteDechets;
    private CentreDeTri emplacement;

    public Vaisseau(int limiteDechets) {

        this.limiteDechets = limiteDechets;

    }


    public  void charge(){



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

    public void setEmplacement(CentreDeTri emplacement) {
        this.emplacement = emplacement;
    }
}
