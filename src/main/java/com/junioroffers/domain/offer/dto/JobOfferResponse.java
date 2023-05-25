package com.junioroffers.domain.offer.dto;

import lombok.Builder;

public record JobOfferResponse(String title,
                               String company,
                               String salary,
                               String offerUrl) {
    @Builder public JobOfferResponse{}
}
