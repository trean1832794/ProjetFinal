package Dechets;

public abstract class Dechet implements Comparable<Dechet> {

        private String nom;
        private float masseVolumique;
        private float pourcentageRecyclage;

        public Dechet (String nom, float masseVolumique, float pourcentageRecyclage) {

            this.nom = nom;
            this.masseVolumique = masseVolumique;
            this.pourcentageRecyclage = pourcentageRecyclage;

        }

    public String getNom() {
        return nom;
    }

    public int compareTo (Dechet o) {

            if (masseVolumique - o.masseVolumique == 0) {

                return 0;

            } else if (masseVolumique-o.masseVolumique < 0) {

                return -1;

            } else {

                return 1;

            }

    }

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
