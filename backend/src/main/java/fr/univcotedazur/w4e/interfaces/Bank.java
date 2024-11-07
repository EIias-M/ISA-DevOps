package fr.univcotedazur.w4e.interfaces;

import fr.univcotedazur.w4e.entities.Customer;

import java.util.Optional;

public interface Bank {

    Optional<String> pay(Customer customer, double value);
}
