package com.junioroffers.domain.offer.exception;

public class OfferDuplicationException extends RuntimeException {
    public OfferDuplicationException() {
        super("Duplication of offer keys");
    }
}
