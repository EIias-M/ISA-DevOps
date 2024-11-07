package fr.univcotedazur.w4e.cli.commands;

import fr.univcotedazur.w4e.cli.CliContext;
import fr.univcotedazur.w4e.cli.model.Activity;
import fr.univcotedazur.w4e.cli.model.Booking;
import fr.univcotedazur.w4e.cli.model.CliCustomer;

import fr.univcotedazur.w4e.cli.model.EntryBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ShellComponent
public class BookingCommands {

    public static final String BASE_URI = "/bookings";

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(Activity.class);

    private final CliContext cliContext;


    @Autowired
    public BookingCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;

    }

    @ShellMethod("book an activity (book ACTIVITY_NAME CUSTOMER_NAME)")
    public Booking bookActivity(String activityName, String customerName) {
        Activity activity = cliContext.getActivities().get(activityName);
        if (activity == null) {
            logger.error("Activity with Name {} not found.", activityName);
            return null;
        }

        CliCustomer cliCustomer = cliContext.getCustomers().get(customerName);
        if (cliCustomer == null) {
            logger.error("Customer with Name {} not found.", customerName);
            return null;
        }

        Booking tempBooking=new Booking(cliCustomer.getId().toString(),activity.getId().toString());
        Booking res = restTemplate.postForObject(BASE_URI+"/create/",tempBooking , Booking.class);
        if (res != null) {
            cliContext.getAllBookings().put(String.valueOf(Objects.requireNonNull(res).getId()), res);
            cliContext.getAllBookings().put(String.valueOf(res.getId()), res);
            cliCustomer.setPointfidele(res.getFidelityPoint());
            cliContext.getCustomers().put(cliCustomer.getName(), cliCustomer);
        } else {
            logger.error("Failed to create Booking. Response is null.");
        }
        return res;
    }


    @ShellMethod("Make an entry book of an activity with (bookEntry NBENTRY ACTIVITY_NAME CUSTOMER_NAME)")
    public Booking bookEntry(int nbEntry,String activityName, String customerName) {
        Activity activity = cliContext.getActivities().get(activityName);
        if (activity == null) {
            logger.error("Activity with Name {} not found.", activityName);
            return null;
        }

        CliCustomer cliCustomer = cliContext.getCustomers().get(customerName);
        if (cliCustomer == null) {
            logger.error("Customer with Name {} not found.", customerName);
            return null;
        }
        Booking tempBooking=new Booking(cliCustomer.getId().toString(),activity.getId().toString());
        EntryBooking resE=new EntryBooking(tempBooking,nbEntry);
        Booking res = restTemplate.postForObject(BASE_URI+"/create/",resE , Booking.class);
        if (resE != null) {
            cliContext.getAllBookings().put(String.valueOf(Objects.requireNonNull(res).getId()), resE);
            cliContext.getAllBookings().put(String.valueOf(res.getId()), resE);
            cliCustomer.setPointfidele(res.getFidelityPoint());
            cliContext.getCustomers().put(cliCustomer.getName(), cliCustomer);
        } else {
            logger.error("Failed to create Booking. Response is null.");
        }
        return res;
    }

    @ShellMethod("list Bookings ")
    public void listBookings() {
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(BASE_URI, Booking[].class);
        System.out.println("Voici la liste de tous les bookings :");
        for (Booking a: response.getBody()) {
            System.out.println(a.toString());
        }
    }


    @ShellMethod("book an activity (book ACTIVITY_NAME GROUP_NAME)")
    public List<Booking> bookGroupActivity(String activityName, String customerNames) {
        String[] names = customerNames.split(",");
        ArrayList<Booking> bookings=new ArrayList<>();
        for (String customerName : names) {
            Activity activity = cliContext.getActivities().get(activityName);
            if (activity == null) {
                logger.error("Activity with Name {} not found.", activityName);
                return null;
            }

            CliCustomer cliCustomer = cliContext.getCustomers().get(customerName);
            if (cliCustomer == null) {
                logger.error("Customer with Name {} not found.", customerName);
                continue;
            }
            Booking tempBooking=new Booking(cliCustomer.getId().toString(),activity.getId().toString());
            Booking res = restTemplate.postForObject(BASE_URI + "/create/", tempBooking, Booking.class);
            if (res != null) {
                cliContext.getAllBookings().put(String.valueOf(res.getId()), res);
                cliCustomer.setPointfidele(res.getFidelityPoint());
                cliContext.getCustomers().put(cliCustomer.getName(), cliCustomer);
                bookings.add(res);
            } else {
                logger.error("Failed to create Booking for Customer {}.", customerName);
            }
        }
        return bookings;
    }

}
