package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

class OfferFacadeTestConfiguration {

    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    private final InMemoryOfferRepository inMemoryOfferRepository;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(
                List.of(
                        new JobOfferResponse("Java Developer", "Example1", "9000PLN", "https://example.com/offer1" ),
                        new JobOfferResponse("Software Engineer", "Example2", "9500PLN", "https://example.com/offer2" ),
                        new JobOfferResponse("Data Analyst", "Example3", "10000PLN", "https://example.com/offer3" ),
                        new JobOfferResponse("Product Manager", "Example4", "12000PLN", "https://example.com/offer4" ),
                        new JobOfferResponse("HR", "Example5", "9250PLN", "https://example.com/offer5" )
                )
        );
        this.inMemoryOfferRepository = new InMemoryOfferRepository();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponse> remoteClientOffers) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.inMemoryOfferRepository = new InMemoryOfferRepository();
    }

    OfferFacade offerFacadeConfigForTests() {
        return new OfferFacade(inMemoryOfferRepository, new OfferService(inMemoryFetcherTest,inMemoryOfferRepository));
    }
}
