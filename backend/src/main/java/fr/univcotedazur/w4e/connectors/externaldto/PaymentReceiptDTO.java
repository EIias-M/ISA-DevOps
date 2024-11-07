package fr.univcotedazur.w4e.connectors.externaldto;

// External DTO to receive a payment receipt from a successful POST payment request to the external Bank system
public record PaymentReceiptDTO (String payReceiptId, double amount)
{
}

