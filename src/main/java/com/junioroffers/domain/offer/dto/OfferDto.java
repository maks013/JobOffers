package com.junioroffers.domain.offer.dto;

import lombok.Builder;

import java.io.Serializable;

public record OfferDto(String id,
                       String companyName,
                       String position,
                       String salary,
                       String offerUrl) implements Serializable {
    @Builder public OfferDto{}
}
