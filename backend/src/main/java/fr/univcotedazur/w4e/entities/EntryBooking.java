package fr.univcotedazur.w4e.entities;

import jakarta.validation.constraints.NotNull;

public class EntryBooking extends Booking{

    public int getNbEntry() {
        return nbEntry;
    }

    public void setNbEntry(int nbEntry) {
        this.nbEntry = nbEntry;
    }

    @NotNull
    int nbEntry;

    public EntryBooking(){

    }

    public EntryBooking(Booking b,int res){
        super(b.getId(),b.getCustomer(),b.getDateReservation(),b.getActivity(),b.getPaymentReceiptId());
        this.nbEntry=res;
    }

    @Override
    public String toString() {
        return "EntryBooking{" +
                "nbEntry=" + nbEntry +
                "} " + super.toString();
    }

}
