package com.junioroffers.infrastructure.offer.http;

import com.junioroffers.domain.offer.OfferFetcher;
import com.junioroffers.domain.offer.dto.JobOfferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetcher {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        try {
            final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService()).toUriString();

            ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });

            final List<JobOfferResponse> body = response.getBody();
            if (body == null) {
                log.info("Response body is null");
                return Collections.emptyList();
            }
            log.info("Response body returned succesfully: " + body);
            return body;
        } catch (ResourceAccessException e) {
            log.error("Fetching offers failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private String getUrlForService() {
        return uri + ":" + port + "/offers";
    }
}
