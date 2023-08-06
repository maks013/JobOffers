package com.junioroffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferFacadeConfig {


    @Bean
    OfferFacade offerFacade(OfferFetcher fetcher){
        OfferRepository repo = new OfferRepository() {
            @Override
            public boolean existsOfferByUrl(String offerUrl) {
                return false;
            }

            @Override
            public Optional<Offer> findOfferByUrl(String offerUrl) {
                return Optional.empty();
            }

            @Override
            public List<Offer> saveAll(List<Offer> offers) {
                return null;
            }

            @Override
            public List<Offer> findAll() {
                return null;
            }

            @Override
            public Optional<Offer> findById(String id) {
                return Optional.empty();
            }

            @Override
            public Offer save(Offer offer) {
                return null;
            }
        };
        OfferService service = new OfferService(fetcher, repo);
        return new OfferFacade(repo, service);
    }

}
