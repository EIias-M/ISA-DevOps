package fr.univcotedazur.w4e.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

@Entity
public class Activity extends Purchasable{

    @Id
    @GeneratedValue
    private int id;

    private float price;
    private int nbSlot;

    @NotBlank
    @Column(unique = true)
    private  String nameActivity;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Activity() {

    }

    public Activity(int id, float price, String nameActivity, int nbSlot) {
        this.id = id;
        this.price = price;
        this.nameActivity = nameActivity;
        this.nbSlot = nbSlot;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNbSlot() {
        return nbSlot;
    }

    public void setNbSlot(int nbSlot) {
        this.nbSlot = nbSlot;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }


    public String getnameActivity() {
        return nameActivity;
    }

    public void setnameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", Price=" + price +
                ", name of the activity ='" + nameActivity + '\'' +
                ", number of slots =" + nbSlot +
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
