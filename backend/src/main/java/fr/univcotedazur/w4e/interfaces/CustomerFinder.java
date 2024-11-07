package fr.univcotedazur.w4e.interfaces;

import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.exceptions.CustomerIdNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerFinder {

    Optional<Customer> findByName(String name);

    Optional<Customer> findById(Long id);

    Customer retrieveCustomer(Long customerId) throws CustomerIdNotFoundException;

    List<Customer> findAll();

}
