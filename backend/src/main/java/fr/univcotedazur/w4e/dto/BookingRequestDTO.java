package fr.univcotedazur.w4e.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BookingRequestDTO(
    @NotBlank(message = "customerId should not be blank") String customer,
    @NotBlank( message = "activityId should not be blank") String activity,
    @NotBlank( message = "nbSlot should not be blank") int nbEntry

    ){
}
