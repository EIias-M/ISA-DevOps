package fr.univcotedazur.w4e.interfaces;


import fr.univcotedazur.w4e.entities.Booking;
import fr.univcotedazur.w4e.exceptions.PaymentException;


import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingCreator {

   // Long createBooking(Long activityId, Long customerId);
    public Booking createBookingPayement(Long customerId, Long activityId,int nbSlot) throws PaymentException;
    public Optional<Booking> finByID(LocalDate dater);

    List<Booking> getAllBookings();
}
