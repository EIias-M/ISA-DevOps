package fr.univcotedazur.w4e.cli.model;

import java.util.Objects;

public class Activity {
    private Long id;
    private float price; // Modifier le type de prix en float
    private String nameActivity;
    private int nbSlot;

    public Activity() {

    }


    public Activity(float price, String nomActivite, int nbSlot) {
        this.price = price;
        this.nameActivity = nomActivite;
        this.nbSlot = nbSlot;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getnameActivity() {
        return nameActivity;
    }

    public void setnameActivity(String nomActivite) {
        this.nameActivity = nomActivite;
    }

    public int getNbSlot() {
        return nbSlot;
    }

    public void setNbSlot(int nbSlot) {
        this.nbSlot = nbSlot;
    }

    public Activity(Long id, float price, String nomActivite, int nbSlot) {
        this.id = id;
        this.price = price;
        this.nameActivity = nomActivite;
        this.nbSlot = nbSlot;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", Prix=" + price +
                ", nomActivite='" + nameActivity + '\'' +
                ", nbPlaces=" + nbSlot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity activity)) return false;
        return Objects.equals(nameActivity, activity.nameActivity) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, price, nameActivity, nbSlot);
    }
}
