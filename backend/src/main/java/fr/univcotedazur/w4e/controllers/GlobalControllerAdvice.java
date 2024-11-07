package fr.univcotedazur.w4e.controllers;

import fr.univcotedazur.w4e.dto.ErrorDTO;
import fr.univcotedazur.w4e.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {CustomerCareController.class})
public class GlobalControllerAdvice {

    @ExceptionHandler({CustomerIdNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(CustomerIdNotFoundException e) {
        return new ErrorDTO("Customer not found", e.getId() + " is not a valid customer Id");
    }

    @ExceptionHandler({OrderIdNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(OrderIdNotFoundException e) {
        return new ErrorDTO("Order not found", e.getId() + " is not a valid order Id");
    }

    @ExceptionHandler({EmptyCartException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(EmptyCartException e) {
        return new ErrorDTO("Cart is empty", "from Customer " + e.getName());
    }

    @ExceptionHandler({PaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleExceptions(PaymentException e) {
        return new ErrorDTO("Payment was rejected", "from Customer " + e.getName() + " for amount " + e.getAmount());
    }


}
