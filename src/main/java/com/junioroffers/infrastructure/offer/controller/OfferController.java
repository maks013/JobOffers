package com.junioroffers.infrastructure.offer.controller;

import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/offers")
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferRequestDto offerDto) {
        try {
            OfferDto offer = offerFacade.saveOffer(offerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        } catch (NullPointerException nullPointerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DuplicateKeyException duplicateKeyException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable String id) {
        offerFacade.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
