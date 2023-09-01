package com.junioroffers.domain.offer.dto;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record OfferRequestDto(
        @NotNull(message = "companyName must not be null") @NotEmpty(message = "companyName must not be empty")
        String companyName,
        @NotNull(message = "position must not be null") @NotEmpty(message = "position must not be empty")
        String position,
        @NotNull(message = "salary must not be null") @NotEmpty(message = "salary must not be empty")
        String salary,
        @NotNull(message = "offerUrl must not be null") @NotEmpty(message = "offerUrl must not be empty")
        String offerUrl) {
    @Builder
    public OfferRequestDto {
    }
}
