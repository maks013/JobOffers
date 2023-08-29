package com.junioroffers.infrastructure.offer.exception;

import com.junioroffers.domain.offer.exception.OfferNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OfferControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    public OfferErrorResponse handleOfferNotFound() {
        return new OfferErrorResponse("Offer not found", HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public OfferErrorResponse handleDuplicateKey() {
        return new OfferErrorResponse("Url duplicated", HttpStatus.CONFLICT);
    }
}
