package CentresDeTri;

import Vaisseaux.Vaisseau;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import Dechets.*;

public class CentreDeTri {
    private int limitePiles = 50;
    private int maxAttente;
    private CentreDeTri nextCentre;
    private Queue<Vaisseau> vaisseauxAttente = new LinkedList<>();
    private Stack<Plutonium> plutonium = new Stack<Plutonium>();
    private Stack<Thulium> thulium = new Stack<Thulium>();
    private Stack<Gadolinium> gadolinium = new Stack<Gadolinium>();
    private Stack<Terbium> terbium = new Stack<Terbium>();
    private Stack<Neptunium> neptunium = new Stack<Neptunium>();

    public CentreDeTri (int maxAttente){
        this.maxAttente = maxAttente;
    }

    public void mettreAttente(Vaisseau vaisseau){
        if(vaisseauxAttente.size() == maxAttente){

        }
        vaisseauxAttente.add(vaisseau);
    }

    public void envoyerVaisseau(Vaisseau vaisseau){

            vaisseau.changerEmplacement(nextCentre);
            vaisseau.decharge();

    }

    public void dechargerVaisseau(Vaisseau vaisseau) {
        ArrayList<Dechet> dechetsTransfer = new ArrayList<>();
        dechetsTransfer.addAll(vaisseau.getDechets());
        for (Dechet dechet : vaisseau.getDechets()) {

            switch (dechet.getNom()){
                case"Plutonium" :
                    if(plutonium.size() < limitePiles) {
                        plutonium.add(new Plutonium());
                        dechetsTransfer.remove(dechet);
                    }else{

                    }
                    break;
                case"Thulium" :
                    if(thulium.size() < limitePiles) {
                        thulium.add(new Thulium());
                        dechetsTransfer.remove(dechet);
                    }
                    break;
                case"Gadolinium" :
                    if(gadolinium.size() < limitePiles) {
                        gadolinium.add(new Gadolinium());
                        dechetsTransfer.remove(dechet);
                    }
                    break;
                case"Terbium" :
                    if(terbium.size() < limitePiles){
                        terbium.add(new Terbium());
                        dechetsTransfer.remove(dechet);
                    }

                    break;
                case"Neptunium" :
                    if(neptunium.size() < limitePiles) {
                        neptunium.add(new Neptunium());
                        dechetsTransfer.remove(dechet);
                    }
                    break;
            }

        }
        vaisseau.setDechets(dechetsTransfer);

    }

    public void decharge(Stack<Dechet> pilePleine){
        vaisseauxAttente.peek().charge(pilePleine);
        envoyerVaisseau(vaisseauxAttente.poll());
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

    public Stack<Plutonium> getPlutonium() {
        return plutonium;
    }

    public void setPlutonium(Stack<Plutonium> plutonium) {
        this.plutonium = plutonium;
    }

    public Stack<Thulium> getThulium() {
        return thulium;
    }

    public void setThulium(Stack<Thulium> thulium) {
        this.thulium = thulium;
    }

    public Stack<Gadolinium> getGadolinium() {
        return gadolinium;
    }

    public void setGadolinium(Stack<Gadolinium> gadolinium) {
        this.gadolinium = gadolinium;
    }

    public Stack<Terbium> getTerbium() {
        return terbium;
    }

    public void setTerbium(Stack<Terbium> terbium) {
        this.terbium = terbium;
    }

    public Stack<Neptunium> getNeptunium() {
        return neptunium;
    }

    public void setNeptunium(Stack<Neptunium> neptunium) {
        this.neptunium = neptunium;
    }

    public CentreDeTri getNextCentre() {
        return nextCentre;
    }

    public void setNextCentre(CentreDeTri nextCentre) {
        this.nextCentre = nextCentre;
    }
}
