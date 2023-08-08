package com.junioroffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferFacadeConfig {

    @Bean
    OfferFacade offerFacade(OfferFetcher fetcher, OfferRepository repo){
        OfferService service = new OfferService(fetcher,repo);
        return new OfferFacade(repo, service);
    }
}
