package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

interface OfferFetcher {
    List<JobOfferResponse> fetchOffers();
}
