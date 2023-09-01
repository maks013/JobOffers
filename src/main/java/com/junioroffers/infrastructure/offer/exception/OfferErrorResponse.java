package com.junioroffers.infrastructure.offer.exception;

import org.springframework.http.HttpStatus;

public record OfferErrorResponse(String message,
                                 HttpStatus status) {
}
