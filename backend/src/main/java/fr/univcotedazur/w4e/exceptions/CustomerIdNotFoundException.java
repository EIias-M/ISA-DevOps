package fr.univcotedazur.w4e.exceptions;

public class CustomerIdNotFoundException extends Exception {

    private Long id;

    public CustomerIdNotFoundException(Long id) {
        this.id = id;
    }

    public CustomerIdNotFoundException() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
