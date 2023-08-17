package com.junioroffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.ExampleResponse;
import com.junioroffers.domain.loginandregister.dto.RegisterDto;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.infrastructure.offer.scheduler.OfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserWantToSeeOffersScenarioIntegrationTest extends BaseIntegrationTest implements ExampleResponse {

    @Autowired
    OfferScheduler offerScheduler;

    @Test
    void user_should_see_offers_all_steps() throws Exception {
        //1. There are no offers on the external server.
        //given, when, then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(zeroOffersBody())
                ));


        //2. First retrieval of 0 offers into the database.
        //given, when
        List<OfferDto> offerDtoList = offerScheduler.fetchOffersAndSave();
        //then
        assertThat(offerDtoList).hasSize(0);


        //3. The user attempts to obtain a JWT token by sending a POST request with the data username=user and password=password. The system returns an error 401.
        //given, when
        ResultActions postLogin = mockMvc.perform(post("/login")
                .content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResultForLogin = postLogin.andExpect(status().isOk()).andReturn();
        String loginJson = mvcResultForLogin.getResponse().getContentAsString();
        RegisterDto registerDto = objectMapper.readValue(loginJson, RegisterDto.class);
        assertThat(registerDto.username()).isEqualTo("user");


        //4. The user tries to retrieve offers without a token, and the system returns error 401.
        //5. The user registers with the data username=user, password=password, and receives a 200 response.
        //6. The user tries to obtain a token again with the data username=user, password=password. The system returns a token with a 200 status.
        //7. Three offers appear on the external server.


        //8. The second retrieval from the external system is made, and three offers are retrieved (ids: 1111, 2222, 3333).
        //given, when
        ResultActions getOffers = mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON));

        //then
        MvcResult mvcResultForOffers = getOffers.andExpect(status().isOk()).andReturn();
        String offersJson = mvcResultForOffers.getResponse().getContentAsString();
        List<OfferDto> offers = objectMapper.readValue(offersJson, new TypeReference<List<OfferDto>>() {
        });
        assertThat(offers).hasSize(0);


        //   9. The authorized user sends a GET request to /offers and receives three offers from the database with a 200 status.
        //   10. The authorized user sends a GET request to /offers/1111, and the system returns the offer with that ID with a 200 status.


        //   11. The authorized user sends a GET request to /offers/4444, and the system returns a 404 status - not found.
        ResultActions getNotExistingOffer = mockMvc.perform(get("/offers/NotExistingId").contentType(MediaType.APPLICATION_JSON));
        getNotExistingOffer.andExpect(status().isNotFound());
    }
}
