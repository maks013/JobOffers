package com.junioroffers.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.JobOffersApplication;
import com.junioroffers.infrastructure.offer.scheduler.OfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

class UserWantToSeeOffersScenarioIntegrationTest extends BaseIntegrationTest {

    @Autowired
    OfferScheduler offerScheduler;

    @Test
    void user_should_see_offers_all_steps() {
        //1. There are no offers on the external server.
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-type","application/json")
                            .withBody("[]")
                ));
        //2. First retrieval of 0 offers into the database.
        await().atMost(Duration.ofSeconds(20)).until(()->false);
        offerScheduler.fetchOffersAndSave();

        //   3. The user attempts to obtain a JWT token by sending a POST request with the data username=user and password=password. The system returns an error 401.
     //   4. The user tries to retrieve offers without a token, and the system returns error 401.
     //   5. The user registers with the data username=user, password=password, and receives a 200 response.
     //   6. The user tries to obtain a token again with the data username=user, password=password. The system returns a token with a 200 status.
     //   7. Three offers appear on the external server.
     //   8. The second retrieval from the external system is made, and three offers are retrieved (ids: 1111, 2222, 3333).
     //   9. The authorized user sends a GET request to /offers and receives three offers from the database with a 200 status.
     //   10. The authorized user sends a GET request to /offers/1111, and the system returns the offer with that ID with a 200 status.
     //   11. The authorized user sends a GET request to /offers/4444, and the system returns a 404 status - not found.
    }
}
