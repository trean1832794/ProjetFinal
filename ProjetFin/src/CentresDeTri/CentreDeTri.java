package CentresDeTri;

import Vaisseaux.Vaisseau;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import Dechets.*;

public class CentreDeTri {
    private int limitePiles = 50;
    private Queue<Vaisseau> vaisseauxAttente = new LinkedList<>();
    private Stack<Plutonium> plutonium = new Stack<Plutonium>();
    private Stack<Thulium> thulium = new Stack<Thulium>();
    private Stack<Gadolinium> gadolinium = new Stack<Gadolinium>();
    private Stack<Terbium> terbium = new Stack<Terbium>();
    private Stack<Neptunium> neptunium = new Stack<Neptunium>();

    public void mettreAttente(Vaisseau vaisseau){
        vaisseauxAttente.add(vaisseau);
    }

    public void envoyerVaisseau(Vaisseau vaisseau){
        vaisseauxAttente.poll();
    }

    public void dechargerVaisseau(Vaisseau vaisseau) {

        for (Dechet dechet : vaisseau.getDechets()) {



        }

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
}
