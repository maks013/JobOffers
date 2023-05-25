package com.junioroffers.domain.offer.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException() {
        super("Offer not found");
    }
}
