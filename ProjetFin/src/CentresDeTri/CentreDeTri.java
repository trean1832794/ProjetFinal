package CentresDeTri;

import Exceptions.MaterialFullException;
import Exceptions.WaitingFullException;
import Vaisseaux.Vaisseau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import Dechets.*;
import Simulation.*;

public class CentreDeTri implements Serializable {
    private int limitePiles = 50;
    private int maxAttente;
    private CentreDeTri nextCentre;
    private CentreDeTri previousCentre;
    private Queue<Vaisseau> vaisseauxAttente = new LinkedList<>();
    private ArrayList<Stack<Dechet>> pilesDechets = new ArrayList<>();

    public CentreDeTri (int maxAttente,ArrayList<Dechet> dechets){
        this.maxAttente = maxAttente;
        //ajouter des piles pour le nombre de déchets différents
        for (int i = 0; i < dechets.size(); i++) {

            pilesDechets.add(new Stack<Dechet>());

        }

    }

    public void mettreAttente(Vaisseau vaisseau){
        if(vaisseauxAttente.size() == maxAttente){
            attentePleine(vaisseauxAttente.peek());
        }
        try {

            Main.checkAttente();

        } catch (WaitingFullException ex) {

            System.out.println("Erreur: " + ex.toString());

        }
        vaisseauxAttente.add(vaisseau);
        //stagnant();
    }

    public void envoyerVaisseau(Vaisseau vaisseau){


            vaisseau.changerEmplacement(nextCentre);
            vaisseau.decharge();

    }

    public void stagnant(){

        if(previousCentre.getVaisseauxAttente().size() == 0 && vaisseauxAttente.size() > 0 && Main.simulationStartee){
            vaisseauxAttente.peek().changerEmplacement(nextCentre);
            vaisseauxAttente.remove().charge(Main.planetes.get((int)(Math.random()*Main.planetes.size())));

        }
    }

    public void attentePleine(Vaisseau vaisseau){

        int quantiteDechets = 0;
        for(Stack<Dechet> dechets : pilesDechets){
            quantiteDechets += dechets.size();
        }
        if(quantiteDechets == 0){
            vaisseau.charge(Main.planetes.get((int)(Math.random()*Main.planetes.size())));
            vaisseauxAttente.remove();
        }else{

            while(true){
                int choix = (int)(Math.random()*pilesDechets.size());
                if(pilesDechets.get(choix).size() > 0){
                    recycler(pilesDechets.get(choix));
                    pilesDechets.get(choix).clear();
                    break;
                }
            }

        }

       /* if(plutonium.size() + thulium.size() + gadolinium.size() + terbium.size() + neptunium.size() == 0){
            vaisseau.charge(Main.planetes.get((int)(Math.random()*Main.planetes.size())));
            vaisseauxAttente.remove();
        }else{
            boolean parti = false;
            while (!parti){
                switch ((int)(Math.random()*5)){
                    case 0 :
                        if(plutonium.size() > 0){
                           recycler(plutonium);
                           plutonium.clear();
                           parti = true;
                        }
                        break;
                    case 1 :
                        if(thulium.size() > 0){
                            recycler(thulium);
                            thulium.clear();
                            parti = true;
                        }
                        break;
                    case 2 :
                        if(gadolinium.size() > 0){
                            recycler(gadolinium);
                            gadolinium.clear();
                            parti = true;
                        }
                        break;
                    case 3 :
                        if(terbium.size() > 0){
                            recycler(terbium);
                            terbium.clear();
                            parti = true;
                        }
                        break;
                    case 4 :
                        if(neptunium.size() > 0){
                            recycler(neptunium);
                            neptunium.clear();
                            parti = true;
                        }
                        break;
                }
            }
        }*/
    }

    public void dechargerVaisseau(Vaisseau vaisseau) {

        ArrayList<Dechet> dechetsTransfer = new ArrayList<>();
        dechetsTransfer.addAll(vaisseau.getDechets());

        for (Dechet dechet : vaisseau.getDechets()){
            if( pilesDechets.get(dechet.getId()).size() < limitePiles) {
                pilesDechets.get(dechet.getId()).add(dechet);
                dechetsTransfer.remove(dechet);
            }else{
                try {

                    Main.exceptionPilePleine();

                } catch (MaterialFullException ex) {

                    System.out.println("Erreur: " + ex.toString());

                }
                if(vaisseauxAttente.size() > 0){
                    vaisseau.setDechets(dechetsTransfer);
                    recycler(pilesDechets.get(dechet.getId()));
                }else{
                    pilesDechets.get(dechet.getId()).clear();
                }
            }
        }
        vaisseau.setDechets(dechetsTransfer);
        mettreAttente(vaisseau);
       /* for (Dechet dechet : vaisseau.getDechets()) {

            switch (dechet.getNom()){
                case"Plutonium" :
                    if(plutonium.size() < limitePiles) {
                        plutonium.add(new Plutonium());
                        dechetsTransfer.remove(dechet);
                    }else{

                        try {

                            Main.exceptionPilePleine();

                        } catch (MaterialFullException ex) {

                            System.out.println("Erreur: " + ex.toString());

                        }
                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(plutonium);

                        } else {

                            plutonium.clear();

                        }


                    }
                    break;
                case"Thulium" :
                    if(thulium.size() < limitePiles) {
                        thulium.add(new Thulium());
                        dechetsTransfer.remove(dechet);
                    } else {
                        try {

                            Main.exceptionPilePleine();

                        } catch (MaterialFullException ex) {

                            System.out.println("Erreur: " + ex.toString());

                        }
                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(thulium);

                        } else {

                            thulium.clear();

                        }

                    }
                    break;
                case"Gadolinium" :
                    if(gadolinium.size() < limitePiles) {
                        gadolinium.add(new Gadolinium());
                        dechetsTransfer.remove(dechet);
                    } else {
                        try {

                            Main.exceptionPilePleine();

                        } catch (MaterialFullException ex) {

                            System.out.println("Erreur: " + ex.toString());

                        }
                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(gadolinium);

                        } else {

                            gadolinium.clear();

                        }

                    }
                    break;
                case"Terbium" :
                    if(terbium.size() < limitePiles){
                        terbium.add(new Terbium());
                        dechetsTransfer.remove(dechet);
                    } else {
                        try {

                            Main.exceptionPilePleine();

                        } catch (MaterialFullException ex) {

                            System.out.println("Erreur: " + ex.toString());

                        }
                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(terbium);

                        } else {

                            terbium.clear();

                        }

                    }

                    break;
                case"Neptunium" :
                    if(neptunium.size() < limitePiles) {
                        neptunium.add(new Neptunium());
                        dechetsTransfer.remove(dechet);
                    } else {
                        try {

                            Main.exceptionPilePleine();

                        } catch (MaterialFullException ex) {

                            System.out.println("Erreur: " + ex.toString());

                        }
                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(neptunium);

                        } else {

                            neptunium.clear();

                        }

                    }
                    break;
            }

        }

        vaisseau.setDechets(dechetsTransfer);
        mettreAttente(vaisseau);*/

    }

    public void recycler (Stack<Dechet> pile) {

        int dechetsRetire;
        dechetsRetire = (int)pile.peek().getPourcentageRecyclage()*pile.size();
        for (int i = 0; i < dechetsRetire; i++) {

            pile.pop();

        }

        decharge(pile);

    }

    public void afficherEtat(){
        System.out.println("Nombre de vaisseau(x) en attente : "+vaisseauxAttente.size());

        for(int i = 0; i < pilesDechets.size(); i++) {

            System.out.println("Quantité de " + Main.dechets.get(i).getNom() + " : " + pilesDechets.get(i).size());

        }

    }

    public void decharge(Stack<Dechet> pilePleine){
        try {

            vaisseauxAttente.peek().charge(pilePleine);

        } catch (NullPointerException ex) {

            System.out.println("Erreur lors de la décharge dans le centre de tri : " + ex.toString());

        }

        envoyerVaisseau(vaisseauxAttente.remove());
    }


    public int getLimitePiles() {
        return limitePiles;
    }

    public void setLimitePiles(int limitePiles) {
        this.limitePiles = limitePiles;
    }

    public Queue<Vaisseau> getVaisseauxAttente() {
        return vaisseauxAttente;
    }

    public void setVaisseauxAttente(Queue<Vaisseau> vaisseauxAttente) {
        this.vaisseauxAttente = vaisseauxAttente;
    }

    public CentreDeTri getNextCentre() {
        return nextCentre;
    }

    public void setNextCentre(CentreDeTri nextCentre) {
        this.nextCentre = nextCentre;
    }

    public CentreDeTri getPreviousCentre() {
        return previousCentre;
    }

    public void setPreviousCentre(CentreDeTri previousCentre) {
        this.previousCentre = previousCentre;
    }

    public ArrayList<Stack<Dechet>> getPilesDechets() {
        return pilesDechets;
    }

    public int getMaxAttente() {
        return maxAttente;
    }

    public void setMaxAttente(int maxAttente) {
        this.maxAttente = maxAttente;
    }
}
