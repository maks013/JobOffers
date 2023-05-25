package com.junioroffers.domain.offer.dto;

import lombok.Builder;

public record OfferDto(String id,
                       String companyName,
                       String position,
                       String salary,
                       String offerUrl) {
    @Builder public OfferDto{}
}
