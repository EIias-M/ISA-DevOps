package fr.univcotedazur.w4e.repositories;

import fr.univcotedazur.w4e.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByDateReservation(LocalDate dateR);
    @Override
    List<Booking> findAll();
}
