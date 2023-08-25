package com.junioroffers.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.junioroffers.ExampleResponse;
import com.junioroffers.domain.offer.OfferFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

class OfferFetcherRestTemplateTest implements ExampleResponse {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    OfferFetcher offerFetcher = new OfferFetcherRestTemplateTestConfig().remoteOfferFetcherClient(
            wireMockServer.getPort(), 1000, 1000
    );

    @Test
    void should_throw_500_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)
                ));
        //when
        Throwable throwable = catchThrowable(() -> offerFetcher.fetchOffers());
        //then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(ResponseStatusException.class),
                () -> assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR")
        );
    }

    @Test
    void should_throw_204_when_status_is_204_no_content() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withHeader("Content-type", "application/json")
                        //status still 204
                        .withBody(threeOffersBody())
                ));
        //when
        Throwable throwable = catchThrowable(() -> offerFetcher.fetchOffers());
        //then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(ResponseStatusException.class),
                () -> assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT")
        );
    }
}
