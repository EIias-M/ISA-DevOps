package fr.univcotedazur.w4e.components;

import fr.univcotedazur.w4e.connectors.BankProxy;
import fr.univcotedazur.w4e.connectors.SchedulerProxy;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.entities.Booking;

import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.exceptions.PaymentException;
import fr.univcotedazur.w4e.interfaces.BookingCreator;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import fr.univcotedazur.w4e.repositories.BookingRepository;
import fr.univcotedazur.w4e.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class BookingManager implements BookingCreator {
    private final BookingRepository bookingRepository;
    private final BankProxy bankProxy;
    private final SchedulerProxy schedulerProxy;
    private final CustomerRepository customerRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public BookingManager(BookingRepository bookingRepository, BankProxy bankProxy, SchedulerProxy schedulerProxy, CustomerRepository customerRepository, ActivityRepository activityRepository) {
        this.bookingRepository = bookingRepository;
        this.bankProxy = bankProxy;
        this.schedulerProxy = schedulerProxy;
        this.customerRepository = customerRepository;
        this.activityRepository = activityRepository;
    }

    private int convertPriceToLoyaltyPoints(double price) {
        // Convertir le prix en points de fidélité (1 point pour chaque 3 euros)
        return (int) (price / 3);
    }


    @Override
    @Transactional
    public Booking createBookingPayement(Long customerId, Long activityId,int nbSlot) throws PaymentException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));
        if(nbSlot>activity.getNbSlot()){
            throw new IllegalArgumentException("Pas assez de slot disponible"+ nbSlot+" > "+activity.getNbSlot());
        }
        if(nbSlot== 0){
            nbSlot=1;
        }
        System.out.println("nbSlot :"+nbSlot);
        int loyaltyPoints = convertPriceToLoyaltyPoints(activity.getPrice()*nbSlot);
        System.out.println("nbSlot2 :"+loyaltyPoints);
        Booking booking = new Booking();
        LocalDate date=LocalDate.now();
        if(schedulerProxy.validate(date).orElseThrow(() -> new PaymentException("Validation failed or returned empty validation."))){
            booking.setDateReservation(LocalDate.now());
        }
        try {
            if (customer.getPointfidele() >= loyaltyPoints) {
                customer.setPointfidele(customer.getPointfidele() - loyaltyPoints);
                booking.setPaymentReceiptId("-"+loyaltyPoints+" Point de fidelité");
            } else {
                String paymentReceiptId = bankProxy.pay(customer, activity.getPrice()*nbSlot)
                        .orElseThrow(() -> new PaymentException("Payment failed or returned empty receipt ID."));
                customer.setPointfidele(loyaltyPoints);
                booking.setPaymentReceiptId(paymentReceiptId);
            }
            customer.addBooking(booking);
            activity.setNbSlot(activity.getNbSlot()-nbSlot);
            booking.setActivity(activityRepository.save(activity));
            booking.setCustomer(customerRepository.save(customer));
            Booking savedBooking = bookingRepository.save(booking);
            return savedBooking;
        } catch (PaymentException e) {
            System.out.println(e.getAmount());
            throw new PaymentException("Payment failed", e.getAmount());
        }
    }

    @Override
    public Optional<Booking> finByID(LocalDate dater) {
        return Optional.ofNullable(bookingRepository.findByDateReservation(dater));
    }
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


}
