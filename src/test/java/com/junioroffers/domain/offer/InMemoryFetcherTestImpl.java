package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

class InMemoryFetcherTestImpl implements OfferFetcher {

    List<JobOfferResponse> offers;

    InMemoryFetcherTestImpl(List<JobOfferResponse> offers) {
        this.offers = offers;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return offers;
    }
}
