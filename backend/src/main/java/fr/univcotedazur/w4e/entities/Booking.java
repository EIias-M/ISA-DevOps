package fr.univcotedazur.w4e.entities;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate dateReservation;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public String getPaymentReceiptId() {
        return paymentReceiptId;
    }

    public void setPaymentReceiptId(String paymentReceiptId) {
        this.paymentReceiptId = paymentReceiptId;
    }

    private String paymentReceiptId;

    public Booking(){
    }

    public Booking(int id, Customer customer, LocalDate dateReservation, Activity activity, String paymentReceiptId) {
        this.id = id;
        this.customer = customer;
        this.dateReservation = dateReservation;
        this.activity = activity;
        this.paymentReceiptId = paymentReceiptId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking{")
                .append("id=").append(id)
                .append(", customer=").append(customer)
                .append(", dateReservation=").append(dateReservation)
                .append(", activity=").append(activity)
                .append(", paymentReceiptId=").append(paymentReceiptId)
                .append('}');
        return sb.toString();
    }
}
