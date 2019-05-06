package Dechets;

public class Dechet implements Comparable<Dechet> {

        private String nom;
        private int id;
        private float masseVolumique;
        private float pourcentageRecyclage;

        public Dechet (String nom, float masseVolumique, float pourcentageRecyclage,int id) {

            this.nom = nom;
            this.masseVolumique = masseVolumique;
            this.pourcentageRecyclage = pourcentageRecyclage;
            this.id = id;

        }

    public String getNom() {
        return nom;
    }

    public int compareTo (Dechet o) {

            if (masseVolumique - o.getMasseVolumique() == 0.0f) {

                return 0;

            } else if (masseVolumique-o.getMasseVolumique() < 0.0f) {

                return -1;

            } else {

                return 1;

            }

    }

    public int getId() { return id;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getMasseVolumique() {
        return masseVolumique;
    }

    public void setMasseVolumique(float masseVolumique) {
        this.masseVolumique = masseVolumique;
    }

    public float getPourcentageRecyclage() {
        return pourcentageRecyclage;
    }

    public void setPourcentageRecyclage(float pourcentageRecyclage) {
        this.pourcentageRecyclage = pourcentageRecyclage;
    }
}
