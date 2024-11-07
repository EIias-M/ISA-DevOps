package fr.univcotedazur.w4e.repositories;

import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.entities.Booking;
import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import fr.univcotedazur.w4e.repositories.BookingRepository;
import fr.univcotedazur.w4e.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void whenSaveBooking_thenItCanBeFound() {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer = customerRepository.save(customer);

        Activity activity = new Activity();
        activity.setnameActivity("Yoga");
        activity = activityRepository.save(activity);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setActivity(activity);
        booking.setDateReservation(LocalDate.now());

        Booking savedBooking = bookingRepository.save(booking);

        Optional<Booking> foundBooking = bookingRepository.findById(Long.valueOf(savedBooking.getId()));
        assertTrue(foundBooking.isPresent());
        assertEquals("John Doe", foundBooking.get().getCustomer().getName());
    }

    @Test
    public void whenFindByCustomer_thenBookingsAreFound() {

        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer = customerRepository.save(customer);

        Activity activity = new Activity();
        activity.setnameActivity("Pilates");
        activity = activityRepository.save(activity);

        Booking booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setActivity(activity);
        booking1.setDateReservation(LocalDate.now());
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setActivity(activity);
        booking2.setDateReservation(LocalDate.now().plusDays(1));
        bookingRepository.save(booking2);

        List<Booking> customerBookings = bookingRepository.findAll();

        assertFalse(customerBookings.isEmpty());
        assertEquals(2, customerBookings.size());
        assertEquals(customer.getName(), customerBookings.get(0).getCustomer().getName());



    }
}
