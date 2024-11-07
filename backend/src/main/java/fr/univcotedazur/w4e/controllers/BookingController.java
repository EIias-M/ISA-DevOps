package fr.univcotedazur.w4e.controllers;

import fr.univcotedazur.w4e.components.BookingManager;
import fr.univcotedazur.w4e.dto.BookingRequestDTO;
import fr.univcotedazur.w4e.dto.BookingResultDTO;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.entities.Booking;
import fr.univcotedazur.w4e.exceptions.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    public static final String BOOK_URI = "/bookings";

    private final BookingManager bookingManager;

    @Autowired
    public BookingController(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    @PostMapping("/create/")
    public ResponseEntity<BookingResultDTO> createBookingAndPayment(@RequestBody BookingRequestDTO tempBooking) {
        try {
            Booking booking = bookingManager.createBookingPayement(Long.parseLong(tempBooking.customer()),Long.parseLong(tempBooking.activity()), tempBooking.nbEntry());
            ResponseEntity<BookingResultDTO> b=ResponseEntity.ok().body(new BookingResultDTO(booking.getId(),booking.getCustomer().getName(),booking.getActivity().getnameActivity(),booking.getDateReservation(),booking.getCustomer().getPointfidele(),booking.getPaymentReceiptId()));
            return b;
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResultDTO[]> listAllBookings() {
        List<Booking> bookings = bookingManager.getAllBookings();
        BookingResultDTO[] bookingArray = new BookingResultDTO[bookings.size()];
        int index = 0;
        for(Booking b:bookings){
            BookingResultDTO bookingDTO = new BookingResultDTO(
                    b.getId(),
                    b.getCustomer().getName(),
                    b.getActivity().getnameActivity(),
                    b.getDateReservation(),
                    b.getCustomer().getPointfidele(),
                    b.getPaymentReceiptId()
            );
            bookingArray[index++] = bookingDTO;
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingArray);

    }

  /*  @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        // Implémentez la logique pour récupérer la réservation à partir de son ID
        return ResponseEntity.notFound().build();
    }*/
}
