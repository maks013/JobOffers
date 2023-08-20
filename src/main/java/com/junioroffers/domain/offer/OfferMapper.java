package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;

class OfferMapper {

    static OfferDto mapToOfferDto(Offer offer){
        return OfferDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    static Offer mapToOfferFromRequestDto(OfferRequestDto offerRequestDto){
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .offerUrl(offerRequestDto.offerUrl())
                .salary(offerRequestDto.salary())
                .build();
    }

    static Offer mapToOfferFromJobResponseDto(JobOfferResponse jobOfferResponse) {
        return Offer.builder()
                .companyName(jobOfferResponse.company())
                .position(jobOfferResponse.title())
                .offerUrl(jobOfferResponse.offerUrl())
                .salary(jobOfferResponse.salary())
                .build();
    }
}
