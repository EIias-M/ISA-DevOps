package fr.univcotedazur.w4e.interfaces;

import java.time.LocalDate;
import java.util.Optional;

public interface Scheduler {
    Optional<Boolean> validate(LocalDate date);

}
