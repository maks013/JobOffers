package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.exception.OfferDuplicationException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferService {

    private final OfferFetcher offerFetcher;
    private final OfferRepository offerRepository;

    List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        final List<Offer> offers = filterNotExisting(jobOffers);
        try {
            return offerRepository.saveAll(offers);
        } catch (OfferDuplicationException duplicationException) {
            throw new OfferDuplicationException();
        }
    }

    private List<Offer> filterNotExisting(List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.offerUrl().isEmpty())
                .filter(offerDto -> !offerRepository.existsOfferByOfferUrl(offerDto.offerUrl()))
                .collect(Collectors.toList());
    }


    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapToOfferFromJobResponseDto)
                .toList();
    }
}
