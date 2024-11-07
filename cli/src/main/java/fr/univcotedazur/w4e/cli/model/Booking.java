package fr.univcotedazur.w4e.cli.model;

import java.time.LocalDate;

public class Booking {

    private Long id;

    public int getFidelityPoint() {
        return fidelityPoint;
    }

    public void setFidelityPoint(int fidelityPoint) {
        this.fidelityPoint = fidelityPoint;
    }

    private int fidelityPoint;

    public void setPaymentReceiptId(String paymentReceiptId) {
        this.paymentReceiptId = paymentReceiptId;
    }

    public String getPaymentReceiptId() {
        return paymentReceiptId;
    }

    private String paymentReceiptId;

    private LocalDate dateReservation;


    private String customer;

    public String getCustomerName() {
        return customer;
    }

    public void setCustomerName(String customerName) {
        this.customer = customerName;
    }

    public String getActivityName() {
        return activity;
    }

    public void setActivityName(String activityName) {
        this.activity = activityName;
    }

    private String activity;





    public Booking(){}
    public Booking(String customer,String activity) {
        this.id = id;
        this.customer = customer;
        this.dateReservation = dateReservation;
        this.activity = activity;
        this.paymentReceiptId = paymentReceiptId;
        this.fidelityPoint=fidelityPoint;
    }
    public Booking(Long id, String customer, LocalDate dateReservation, String activity, String paymentReceiptId,int fidelityPoint) {
        this.id = id;
        this.customer = customer;
        this.dateReservation = dateReservation;
        this.activity = activity;
        this.paymentReceiptId = paymentReceiptId;
        this.fidelityPoint=fidelityPoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
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
                .append(", pointfidelité=").append(fidelityPoint)
                .append('}');
        return sb.toString();
    }
}
