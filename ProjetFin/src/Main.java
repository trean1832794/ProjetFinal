import CentresDeTri.CentreDeTri;
import Planetes.Planete;
import Vaisseaux.Vaisseau;
import Vaisseaux.VaisseauLeger;
import Vaisseaux.VaisseauLourd;
import Vaisseaux.VaisseauNormal;

import java.util.Scanner;

// ProjetFinal Antoine Tremblay-Simard & Thomas Bergeron
public class Main {


    public static void main(String[] args) {

        Vaisseau[] vaisseaux;
        CentreDeTri[] centresDeTris;
        Planete[] planetes;

        System.out.println("Bienvenue dans la super simulation de la gestion des déchets intergalactiques!");
        //demander le nombre de vaisseaux
        System.out.println("Combien de vaisseaux voulez-vous? : ");
        vaisseaux = new Vaisseau[demanderTaille()];

        for (int i = 0; i < vaisseaux.length; i++) {
            long typeVaisseau = Math.round((Math.random()*2)+1);

            if (typeVaisseau == 1) {

                vaisseaux[i] = new VaisseauLeger();

            }else if (typeVaisseau == 2) {

                vaisseaux[i] = new VaisseauNormal();

            } else {

                vaisseaux[i] = new VaisseauLourd();

            }

        }

        //demander le nombre de centres de tri
        System.out.println("Combien de centres de tri voulez-vous? : ");
        centresDeTris = new CentreDeTri[demanderTaille()];

        //calcul de la limite de vaisseaux en attente par centre de tri
        int limiteAttente = Math.round((vaisseaux.length/centresDeTris.length))+1;

        centresDeTris[centresDeTris.length-1] = new CentreDeTri(limiteAttente);
        for (int i = centresDeTris.length-2; i > 0; i++) {


            centresDeTris[i] = new CentreDeTri(limiteAttente);
            centresDeTris[i].setNextCentre(centresDeTris[i+1]);

        }

        //début de la simulation



    }

    public static int demanderTaille () {

        Scanner sc = new Scanner(System.in);

        int choice = sc.next().charAt(0)-48;
        while (choice <= 0) {

            System.out.println("Veuillez entrer un chiffre plus grand que 0. >:(");
            choice = sc.next().charAt(0)-48;

        }

        return choice;
    }
}
