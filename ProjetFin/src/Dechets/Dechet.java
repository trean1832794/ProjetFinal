package Dechets;

public abstract class Dechet {

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
