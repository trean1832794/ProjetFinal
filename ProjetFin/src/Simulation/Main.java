package Simulation;

import CentresDeTri.CentreDeTri;
import Exceptions.MaterialFullException;
import Exceptions.WaitingFullException;
import Planetes.*;
import Vaisseaux.Vaisseau;
import Vaisseaux.VaisseauLeger;
import Vaisseaux.VaisseauLourd;
import Vaisseaux.VaisseauNormal;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// ProjetFinal Antoine Tremblay-Simard & Thomas Bergeron
public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Planete> planetes  = new ArrayList<>();
    public static ArrayList<Vaisseau> vaisseaux = new ArrayList<>();
    public static CentreDeTri[] centresDeTris;
    public static boolean simulationStartee = false;


    public static void main(String[] args) {

        Node root = null;
        try {
            File file = new File("data.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            root = doc.getDocumentElement(); // Référence à l'élément racine
            clean(root);

        } catch (ParserConfigurationException ex1) {

            System.out.println("Erreur de fichier : " + ex1.toString());

        } catch (SAXException ex2) {

            System.out.println("Erreur de fichier : " + ex2.toString());

        } catch (IOException ex3) {

            System.out.println("Erreur de fichier : " + ex3.toString());

        }


        //gestion des données venues du XML
        //vaisseaux
        if (root !=null) {

            //leger
            for (int i = 0; i < toInt(root.getFirstChild().getFirstChild().getNodeValue()); i++) {

                vaisseaux.add(new VaisseauLeger());

            }
            //normal
            for (int i = 0; i < toInt(root.getFirstChild().getNextSibling().getFirstChild().getNodeValue()); i++) {

                vaisseaux.add(new VaisseauNormal());

            }

            for (int i = 0; i < toInt(root.getLastChild().getFirstChild().getNodeValue()); i++) {

                vaisseaux.add(new VaisseauLeger());

            }

            //planètes
            for(int i = 0; i < root.getChildNodes().item(1).getChildNodes().getLength(); i++) {

                float[] chanceDechet = parsePlanete(root.getChildNodes().item(1).getChildNodes().item(i).getFirstChild().getNodeValue());
                planetes.add(new Planete(chanceDechet));

            }

        }


        //System.out.println("Bienvenue dans la super simulation de la gestion des déchets intergalactiques!");
        //demander le nombre de vaisseaux
       //System.out.println("Combien de vaisseaux voulez-vous? : ");

        //demander le nombre de centres de tri
        //System.out.println("Combien de centres de tri voulez-vous? : ");
        centresDeTris = new CentreDeTri[demanderTaille()];

        //calcul de la limite de vaisseaux en attente par centre de tri
        int limiteAttente = Math.round((vaisseaux.size()/centresDeTris.length))+2;

        centresDeTris[centresDeTris.length-1] = new CentreDeTri(limiteAttente);
        for (int i = centresDeTris.length-2; i >= 0; i--) {


            centresDeTris[i] = new CentreDeTri(limiteAttente);
            centresDeTris[i].setNextCentre(centresDeTris[i+1]);

        }
        centresDeTris[centresDeTris.length-1].setNextCentre(centresDeTris[0]);

        //assigner le centre d'avant
        centresDeTris[0].setPreviousCentre(centresDeTris[centresDeTris.length-1]);
        for (int i = 1; i < centresDeTris.length; i++) {

            centresDeTris[i].setPreviousCentre(centresDeTris[i-1]);

        }

        //début de la simulation
        for (int i = 0; i < vaisseaux.size() -1; i++) {


            vaisseaux.get(i).changerEmplacement(centresDeTris[0]);
            vaisseaux.get(i).charge(planetes.get((int)(Math.random()*planetes.size())));

        }
        //starter la simulation avant d'envoyer le dernier vaisseau pour des raisons pratiques
        simulationStartee = true;
        vaisseaux.get(vaisseaux.size()-1).changerEmplacement(centresDeTris[0]);
        vaisseaux.get(vaisseaux.size()-1).charge(planetes.get((int)(Math.random()*planetes.size())));
        finSimulation();


    }

    public static void exceptionPilePleine()throws MaterialFullException {
        int[] nbDechets = new int[5];

        for (CentreDeTri centre : centresDeTris){
            nbDechets[0] += centre.getPlutonium().size();
            nbDechets[1] += centre.getThulium().size();
            nbDechets[2] += centre.getGadolinium().size();
            nbDechets[3] += centre.getTerbium().size();
            nbDechets[4] += centre.getNeptunium().size();
        }

        for(int nb : nbDechets){
            if(nb == centresDeTris[0].getLimitePiles()*centresDeTris.length){
                throw new MaterialFullException();
            }
        }
    }

    public static void checkAttente() throws WaitingFullException {

        int centresTriPleins = 0;
        for (CentreDeTri centre : centresDeTris) {

            if (centre.getVaisseauxAttente().size() >= centre.getMaxAttente()) {

                centresTriPleins++;

            }

        }

        if (centresTriPleins == centresDeTris.length) {

            throw(new WaitingFullException());

        }

    }

    public static void finSimulation () {

        System.out.println("Fin de la simulation! Voici les stats finales");

        for (int i = 0; i < centresDeTris.length; i++) {

            System.out.println("\n-----------------\nCentre de tri # " + (i+1) + "\n-----------------\n");
            centresDeTris[i].afficherEtat();

        }

    }

    public static int demanderTaille () {

        int choice = nextInt();
        while (choice <= 0) {

            System.out.println("Veuillez entrer un chiffre plus grand que 0. >:(");
            choice = nextInt();

        }

        return choice;
    }

    public static int nextInt() {

        //failsafe dans chaque fois ou on demande un int pour que meme si on mette des lettres ça crash pas le programme
        String nb = (sc.next());
        int nbFinal = 0;
        for(int i = nb.length()-1; i >= 0; i--){
            nbFinal = (int)(nbFinal + (nb.charAt((nb.length()-1) - (i))-48)*Math.pow(10,i));
        }

        return nbFinal;

    }

    public static int toInt(String string) {

        String nb = string;
        int nbFinal = 0;
        for(int i = nb.length()-1; i >= 0; i--){
            nbFinal = (int)(nbFinal + (nb.charAt((nb.length()-1) - (i))-48)*Math.pow(10,i));
        }

        return nbFinal;

    }

    public static float[] parsePlanete(String string) {

        String[] chanceDechet = string.split(";");
        float[] chanceDechetFloat = new float[chanceDechet.length];
        for(int i = 0; i < chanceDechet.length; i++) {
            float nbFinal = 0;
            for(int j = 0; j < chanceDechet[i].length(); j++){
                nbFinal = (int)(nbFinal + (chanceDechet[j].charAt(j)-48)*Math.pow(10,-j));
            }
            chanceDechetFloat[i] = nbFinal;

        }

        return chanceDechetFloat;
    }

    public static void clean(Node node)
    {
        NodeList childNodes = node.getChildNodes();

        for (int n = childNodes.getLength() - 1; n >= 0; n--)
        {
            Node child = childNodes.item(n);
            short nodeType = child.getNodeType();

            if (nodeType == Node.ELEMENT_NODE)
                clean(child);
            else if (nodeType == Node.TEXT_NODE)
            {
                String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0)
                    node.removeChild(child);
                else
                    child.setNodeValue(trimmedNodeVal);
            }
            else if (nodeType == Node.COMMENT_NODE)
                node.removeChild(child);
        }
    }
}
