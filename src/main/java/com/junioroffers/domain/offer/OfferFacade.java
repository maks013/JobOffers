package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferDto> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapToOfferDto)
                .orElseThrow(OfferNotFoundException::new);
    }

    public OfferDto saveOffer(OfferRequestDto offerRequestDto) {
        final Offer offer = OfferMapper.mapToOfferFromRequestDto(offerRequestDto);
        return OfferMapper.mapToOfferDto(offerRepository.save(offer));
    }

    public List<OfferDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper :: mapToOfferDto)
                .toList();
    }

}
