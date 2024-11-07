package fr.univcotedazur.w4e.cli.model;

public class EntryBooking extends Booking{

    public int getNbEntry() {
        return nbEntry;
    }

    public void setNbEntry(int nbEntry) {
        this.nbEntry = nbEntry;
    }

    int nbEntry;

        public EntryBooking(){

        }
        public EntryBooking(Booking b,int res){
            super(b.getId(),b.getCustomer(),b.getDateReservation(),b.getActivity(),b.getPaymentReceiptId(),b.getFidelityPoint());
            this.nbEntry=res;
        }

    @Override
    public String toString() {
        return "EntryBooking{" +
                "nbEntry=" + nbEntry +
                "} " + super.toString();
    }

}
