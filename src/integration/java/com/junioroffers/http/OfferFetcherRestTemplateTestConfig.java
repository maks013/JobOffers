package com.junioroffers.http;

import com.junioroffers.domain.offer.OfferFetcher;
import com.junioroffers.infrastructure.offer.http.OfferHttpConfig;
import org.springframework.web.client.RestTemplate;

class OfferFetcherRestTemplateTestConfig extends OfferHttpConfig {

    public OfferFetcher remoteOfferFetcherClient(int port, int connectionTimeout, int readTimeout) {
        RestTemplate restTemplate = restTemplate(1000, 1000, restTemplateResponseErrorHandler());
        return remoteOfferClient(restTemplate, "http://localhost", port);
    }
}
