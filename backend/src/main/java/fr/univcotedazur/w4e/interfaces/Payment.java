package fr.univcotedazur.w4e.interfaces;

import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.exceptions.PaymentException;

public interface Payment {

    void payOrderFromCart(Customer customer, double price) throws PaymentException;

}
