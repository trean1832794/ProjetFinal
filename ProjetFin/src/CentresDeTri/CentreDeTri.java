package CentresDeTri;

import Vaisseaux.Vaisseau;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import Dechets.*;
import Simulation.*;

public class CentreDeTri {
    private int limitePiles = 50;
    private int maxAttente;
    private CentreDeTri nextCentre;
    private CentreDeTri previousCentre;
    private Queue<Vaisseau> vaisseauxAttente = new LinkedList<>();
    private Stack<Dechet> plutonium = new Stack<Dechet>();
    private Stack<Dechet> thulium = new Stack<Dechet>();
    private Stack<Dechet> gadolinium = new Stack<Dechet>();
    private Stack<Dechet> terbium = new Stack<Dechet>();
    private Stack<Dechet> neptunium = new Stack<Dechet>();

    public CentreDeTri (int maxAttente){
        this.maxAttente = maxAttente;
    }

    public void mettreAttente(Vaisseau vaisseau){
        if(vaisseauxAttente.size() == maxAttente){
            attentePleine(vaisseauxAttente.peek());
        }
        vaisseauxAttente.add(vaisseau);
        //stagnant();
    }

    public void envoyerVaisseau(Vaisseau vaisseau){


            vaisseau.changerEmplacement(nextCentre);
            vaisseau.decharge();

    }

    public void stagnant(){

        if (previousCentre == null) {

            System.out.println("PREVIOUSCENTRE NULL");

        }

        if(previousCentre.getVaisseauxAttente().size() == 0 && vaisseauxAttente.size() > 0 && Main.simulationStartee){
            vaisseauxAttente.peek().changerEmplacement(nextCentre);
            vaisseauxAttente.remove().charge(Main.planetes[(int)(Math.random()*Main.planetes.length)]);

        }
    }

    public void attentePleine(Vaisseau vaisseau){
        if(plutonium.size() + thulium.size() + gadolinium.size() + terbium.size() + neptunium.size() == 0){
            vaisseau.charge(Main.planetes[(int)(Math.random()*Main.planetes.length)]);
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
        }
    }

    public void dechargerVaisseau(Vaisseau vaisseau) {

        if (vaisseau == null) {

            System.out.println("VAISSEAU NULL DANS DECHARGERVAISSEAU");

        }

        ArrayList<Dechet> dechetsTransfer = new ArrayList<>();
        dechetsTransfer.addAll(vaisseau.getDechets());
        for (Dechet dechet : vaisseau.getDechets()) {

            switch (dechet.getNom()){
                case"Plutonium" :
                    if(plutonium.size() < limitePiles) {
                        plutonium.add(new Plutonium());
                        dechetsTransfer.remove(dechet);
                    }else{

                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(plutonium);

                        } else {

                            System.out.println("PILEPLEINE PAS DE VAISSEAU EN ATTENTE");
                            plutonium.clear();

                        }


                    }
                    break;
                case"Thulium" :
                    if(thulium.size() < limitePiles) {
                        thulium.add(new Thulium());
                        dechetsTransfer.remove(dechet);
                    } else {

                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(thulium);

                        } else {

                            System.out.println("PILEPLEINE PAS DE VAISSEAU EN ATTENTE");
                            thulium.clear();

                        }

                    }
                    break;
                case"Gadolinium" :
                    if(gadolinium.size() < limitePiles) {
                        gadolinium.add(new Gadolinium());
                        dechetsTransfer.remove(dechet);
                    } else {

                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(gadolinium);

                        } else {

                            System.out.println("PILEPLEINE PAS DE VAISSEAU EN ATTENTE");
                            gadolinium.clear();

                        }

                    }
                    break;
                case"Terbium" :
                    if(terbium.size() < limitePiles){
                        terbium.add(new Terbium());
                        dechetsTransfer.remove(dechet);
                    } else {

                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(terbium);

                        } else {

                            System.out.println("PILEPLEINE PAS DE VAISSEAU EN ATTENTE");
                            terbium.clear();

                        }

                    }

                    break;
                case"Neptunium" :
                    if(neptunium.size() < limitePiles) {
                        neptunium.add(new Neptunium());
                        dechetsTransfer.remove(dechet);
                    } else {

                        if (vaisseauxAttente.size() > 0) {

                            vaisseau.setDechets(dechetsTransfer);
                            recycler(neptunium);

                        } else {

                            System.out.println("PILEPLEINE PAS DE VAISSEAU EN ATTENTE");
                            neptunium.clear();

                        }

                    }
                    break;
            }

        }

        vaisseau.setDechets(dechetsTransfer);
        mettreAttente(vaisseau);

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
        System.out.println("Quantité de plutonium : "+plutonium.size());
        System.out.println("Quantité de thulium : "+thulium.size());
        System.out.println("Quantité de gadolium : "+gadolinium.size());
        System.out.println("Quantité de terbium : "+terbium.size());
        System.out.println("Quantité de neptunium : "+neptunium.size());
    }

    public void decharge(Stack<Dechet> pilePleine){
        try {

            vaisseauxAttente.peek().charge(pilePleine);

        } catch (NullPointerException ex) {

            System.out.println("NULLPOINTEREXCEPTION DANS DECHARGE CENTREDETRI : " + ex.toString());

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

    public Stack<Dechet> getPlutonium() {
        return plutonium;
    }

    public void setPlutonium(Stack<Dechet> plutonium) {
        this.plutonium = plutonium;
    }

    public Stack<Dechet> getThulium() {
        return thulium;
    }

    public void setThulium(Stack<Dechet> thulium) {
        this.thulium = thulium;
    }

    public Stack<Dechet> getGadolinium() {
        return gadolinium;
    }

    public void setGadolinium(Stack<Dechet> gadolinium) {
        this.gadolinium = gadolinium;
    }

    public Stack<Dechet> getTerbium() {
        return terbium;
    }

    public void setTerbium(Stack<Dechet> terbium) {
        this.terbium = terbium;
    }

    public Stack<Dechet> getNeptunium() {
        return neptunium;
    }

    public void setNeptunium(Stack<Dechet> neptunium) {
        this.neptunium = neptunium;
    }
}
