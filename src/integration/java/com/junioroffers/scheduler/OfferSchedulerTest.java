package com.junioroffers.scheduler;

import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.JobOffersApplication;
import com.junioroffers.domain.offer.OfferFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
class OfferSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    OfferFetcher offerFetcher;

    @Test
    void should_run_fetcher_in_correct_time() {
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(offerFetcher, times(5)).fetchOffers());
    }
}
