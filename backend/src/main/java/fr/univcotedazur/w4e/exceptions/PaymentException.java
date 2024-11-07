package fr.univcotedazur.w4e.exceptions;

public class PaymentException extends Exception {

    private String name;
    private double amount;

    private String message;

    public PaymentException(String customerName, double amount) {
        this.name = customerName;
        this.amount = amount;
    }


    public PaymentException(String message) {
        super(message);
        this.message = message;
    }

    public PaymentException() {
    }

    public String getMessage(){
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
