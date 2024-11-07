package fr.univcotedazur.w4e.interfaces;

import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.exceptions.AlreadyExistingCustomerException;

public interface CustomerRegistration {

    Customer register(String name, String creditCard)
            throws AlreadyExistingCustomerException;

}
