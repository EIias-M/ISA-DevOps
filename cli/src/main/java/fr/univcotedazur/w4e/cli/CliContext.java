package fr.univcotedazur.w4e.cli;

import fr.univcotedazur.w4e.cli.model.Activity;
import fr.univcotedazur.w4e.cli.model.Booking;
import fr.univcotedazur.w4e.cli.model.CliCustomer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CliContext {

    private Map<String, CliCustomer> customers;

    private Map<String, Activity> activities;
    private Map<String, Booking> bookings;



    public Map<String, CliCustomer> getCustomers() {
        return customers;
    }
    public Map<String, Activity> getActivities() {
        return activities;
    }
    public Map<String, Booking> getAllBookings() {
        return bookings;
    }



    public CliContext() {
        customers = new HashMap<>();
        activities = new HashMap<>();
        bookings=new HashMap<>();

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customers: ").append(customers);
        sb.append(", Activities: ").append(activities);
        sb.append(", Bookings: ").append(bookings);
        return sb.toString();
    }



}
