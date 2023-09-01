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
        OfferDto offerDto = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerDto);
    }

    @PostMapping("/offers")
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferRequestDto offerDto) {
        OfferDto offer = offerFacade.saveOffer(offerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(offer);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable String id) {
        offerFacade.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
