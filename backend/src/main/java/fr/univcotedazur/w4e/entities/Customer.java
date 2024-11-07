package fr.univcotedazur.w4e.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;

    @Pattern(regexp = "\\d{10}+", message = "Invalid creditCardNumber")
    private String creditCard;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> bookings= new ArrayList<>();

     private int pointfidele;


    public Customer() {
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getPointfidele() {
        return pointfidele;
    }

    public void setPointfidele(int pointfidele) {
        this.pointfidele = pointfidele;
    }

    public Customer(String n, String c) {
        this.name = n;
        this.creditCard = c;
    }

    public Customer(Long id,String n, String c,int nb) {
        this.id=id;
        this.name = n;
        this.creditCard = c;
        this.pointfidele=nb;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void addBooking(Booking b){
        bookings.add(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(creditCard, customer.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditCard);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CliCustomer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", creditCard='").append(creditCard).append('\'');
        sb.append(", pointfidele=").append(pointfidele);
        /*sb.append(", bookings=[");
        for (Booking booking : bookings) {
            sb.append(booking).append(", ");
        }
        if (!bookings.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length()).append("]");
        } else {
            sb.append("]");
        }
        sb.append('}');*/
        return sb.toString();
    }
}
