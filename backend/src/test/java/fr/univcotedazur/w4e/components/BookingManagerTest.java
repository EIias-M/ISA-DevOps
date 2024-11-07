package fr.univcotedazur.w4e.components;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import fr.univcotedazur.w4e.connectors.BankProxy;
import fr.univcotedazur.w4e.connectors.SchedulerProxy;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.entities.Booking;
import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.exceptions.PaymentException;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import fr.univcotedazur.w4e.repositories.BookingRepository;
import fr.univcotedazur.w4e.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
public class BookingManagerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankProxy bankProxy;

    @Mock
    private SchedulerProxy schedulerProxy;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingManager bookingManager;

    @Test
    public void createBookingPayment_ValidCustomer_ValidActivity_EnoughLoyaltyPoints_ShouldReturnBooking() throws PaymentException {
        Long customerId = 1L;
        int activityId = 1;
        int loyaltyPoints = 50;
        float activityPrice = 150.0F;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setCreditCard("1234567891");
        customer.setPointfidele(loyaltyPoints + 10);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setPrice(activityPrice);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setActivity(activity);
        booking.setDateReservation(LocalDate.now());
        booking.setPaymentReceiptId("123456");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(activityRepository.findById((long) activityId)).thenReturn(Optional.of(activity));
        lenient().when(bankProxy.pay(any(Customer.class), any(Double.class))).thenReturn(Optional.of("1234567891"));
        lenient().when(schedulerProxy.validate(any(LocalDate.class))).thenReturn(Optional.of(true));
        lenient().when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingManager.createBookingPayement(customerId, (long) activityId,activity.getNbSlot());

        assertNotNull(createdBooking);
        assertEquals(customerId, createdBooking.getCustomer().getId());
        assertEquals(activityId, createdBooking.getActivity().getId());
        assertNotNull(createdBooking.getPaymentReceiptId());
        assertEquals(LocalDate.now(), createdBooking.getDateReservation());
        assertEquals("123456", createdBooking.getPaymentReceiptId());
        verify(activityRepository).save(any(Activity.class));
    }
}
