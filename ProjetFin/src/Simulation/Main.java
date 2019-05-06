package Simulation;

import CentresDeTri.CentreDeTri;
import Dechets.Dechet;
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
    public static ArrayList<Dechet> dechets = new ArrayList<>();
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
            NodeList liste = root.getFirstChild().getChildNodes();
            System.out.println(root.getChildNodes().item(1).getChildNodes().item(0).getFirstChild().getTextContent());
            for (int i = 0; i < toInt(root.getFirstChild().getFirstChild().getFirstChild().getTextContent()); i++) {

                vaisseaux.add(new VaisseauLeger());

            }
            //normal
            for (int i = 0; i < toInt(root.getFirstChild().getFirstChild().getNextSibling().getFirstChild().getTextContent()); i++) {

                vaisseaux.add(new VaisseauNormal());

            }

            //lourd
            for (int i = 0; i < toInt(root.getFirstChild().getLastChild().getFirstChild().getTextContent()); i++) {

                vaisseaux.add(new VaisseauLeger());

            }

            //planètes
            for(int i = 0; i < root.getChildNodes().item(1).getChildNodes().getLength(); i++) {

                float[] chanceDechet = parsePlanete(root.getChildNodes().item(1).getChildNodes().item(i).getFirstChild().getTextContent());
                planetes.add(new Planete(chanceDechet));

            }

            //déchets
            for (int i = 0; i < root.getChildNodes().item(2).getChildNodes().getLength(); i++) {

                Node directory = root.getChildNodes().item(2).getChildNodes().item(i);
                Dechet dechet = new Dechet(directory.getChildNodes().item(0).getTextContent(),toFloat((directory.getChildNodes().item(1).getTextContent())),toFloat((directory.getChildNodes().item(2).getTextContent())),i);

            }

            //centres de tri
            int nbCentreTri = toInt(root.getLastChild().getFirstChild().getTextContent());
            if (nbCentreTri > 0) {

                centresDeTris = new CentreDeTri[nbCentreTri];

                //calcul de la limite de vaisseaux en attente par centre de tri
                int limiteAttente = Math.round((vaisseaux.size()/centresDeTris.length))+2;

                centresDeTris[centresDeTris.length-1] = new CentreDeTri(limiteAttente,dechets);
                for (int i = centresDeTris.length-2; i >= 0; i--) {


                    centresDeTris[i] = new CentreDeTri(limiteAttente,dechets);
                    centresDeTris[i].setNextCentre(centresDeTris[i+1]);

                }
                centresDeTris[centresDeTris.length-1].setNextCentre(centresDeTris[0]);

                //assigner le centre d'avant
                centresDeTris[0].setPreviousCentre(centresDeTris[centresDeTris.length-1]);
                for (int i = 1; i < centresDeTris.length; i++) {

                    centresDeTris[i].setPreviousCentre(centresDeTris[i-1]);

                }

            } else {

                System.out.println("Fichier XML Erroné : le nombre de centres de tri est plus petit ou égal à 0. >:(");
                System.exit(6);

            }

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
        int[] nbDechets = new int[dechets.size()];

        for (CentreDeTri centre : centresDeTris){

            for (int i = 0; i < dechets.size(); i++) {

                nbDechets[i] += centre.getPilesDechets().get(i).size();

            }

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

    public static float toFloat(String string) {

        float nbFinal = 0;
        String nb = ""+string.split(",")[0] + string.split(",")[1];
        int j = 0;
        for(int i = string.split(",")[0].length() -1; i >= string.split(",")[0].length()-nb.length(); i--){

            nbFinal = (float)(nbFinal + (nb.charAt(j)-48)*Math.pow(10,i));
            j++;
        }
        return nbFinal;

    }

    public static float[] parsePlanete(String string) {

        String[] chanceDechet = string.split(";");
        float[] chanceDechetFloat = new float[chanceDechet.length];
        for(int i = 0; i < chanceDechet.length; i++) {

            System.out.println(toFloat(chanceDechet[i]));
            chanceDechetFloat[i] = toFloat(chanceDechet[i]);

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
