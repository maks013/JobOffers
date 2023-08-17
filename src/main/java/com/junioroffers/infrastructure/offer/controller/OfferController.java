package com.junioroffers.infrastructure.offer.controller;

import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDto>> findOffers() {
        List<OfferDto> offersResult = offerFacade.findAllOffers();
        return ResponseEntity.ok(offersResult);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable String id) {
        try {
            OfferDto offerDto = offerFacade.findOfferById(id);
            return ResponseEntity.ok(offerDto);
        } catch (OfferNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
