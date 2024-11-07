package fr.univcotedazur.w4e.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BookingResultDTO (
    int id, // expected to be empty when POSTing the creation of Customer, and containing the Id when returned
    @NotBlank(message = "customerId should not be blank") String customer,
    @NotBlank( message = "activityId should not be blank") String activity,
    @NotBlank( message = "date should not be blank") LocalDate dateReservation,
    @NotBlank( message = "fidelityPoint should not be blank") int fidelityPoint,
    @NotBlank( message = "PaymentReceiptId should not be blank") String paymentReceiptId
    ) {
}
