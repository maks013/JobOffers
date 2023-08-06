package com.junioroffers.infrastructure.offer.http;

import com.junioroffers.domain.offer.OfferFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferHttpConfig{

    @Bean
    public ResponseErrorHandler restTemplateResponseErrorHandler() {
        return new ResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${offer.http.client.config.connectionTimeout:1000}") long connectionTimeout,
                                     @Value("${offer.http.client.config.readTimeout:1000}") long readTimeout,
                                     ResponseErrorHandler responseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(responseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public OfferFetcher remoteOfferClient(RestTemplate restTemplate,
                                          @Value("${offer.http.client.config.uri:http://example.com}") String uri,
                                          @Value("${offer.http.client.config.port:5057}") int port) {
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }

}
