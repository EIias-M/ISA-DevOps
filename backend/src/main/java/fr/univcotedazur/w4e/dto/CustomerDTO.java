package fr.univcotedazur.w4e.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Same DTO as input and output (no id in the input)
public record CustomerDTO (
        Long id, // expected to be empty when POSTing the creation of Customer, and containing the Id when returned
        @NotBlank(message = "Name should not be blank") String name,
        @Pattern(regexp = "\\d{10}", message = "Credit card should be exactly 11 digits") String creditCard,
        @Min(value = 0, message = "fidelityPoint should be a positive number")int pointfidele
) {}
