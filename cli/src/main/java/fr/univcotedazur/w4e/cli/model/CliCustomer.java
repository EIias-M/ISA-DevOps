package fr.univcotedazur.w4e.cli.model;

import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private Long id;

    private String name;

    @Pattern(regexp = "\\d{10}+", message = "Invalid creditCardNumber")
    private String creditCard;

    private List<Booking> bookings= new ArrayList<>();

    private int pointfidele;


    public CliCustomer() {
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

    public CliCustomer(String n, String c) {
        this.name = n;
        this.creditCard = c;
        this.pointfidele=0;
    }

    public CliCustomer(Long id,String n, String c,int nb) {
        this.id=id;
        this.name = n;
        this.creditCard = c;
        this.pointfidele=nb;
    }
    public void addBooking(Booking b){
        bookings.add(b);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliCustomer customer = (CliCustomer) o;
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
        sb.append(", bookings=[");
        for (Booking booking : bookings) {
            sb.append(booking).append(", ");
        }
        if (!bookings.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length()).append("]");
        } else {
            sb.append("]");
        }
        sb.append('}');
        return sb.toString();
    }

}
