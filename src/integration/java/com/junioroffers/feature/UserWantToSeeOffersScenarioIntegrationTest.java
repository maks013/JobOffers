package com.junioroffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.ExampleResponse;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.infrastructure.loginandregister.dto.LoginResponseDto;
import com.junioroffers.infrastructure.offer.scheduler.OfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserWantToSeeOffersScenarioIntegrationTest extends BaseIntegrationTest implements ExampleResponse {

    @Autowired
    OfferScheduler offerScheduler;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("offer.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("offer.http.client.config.port", () -> wireMockServer.getPort());
    }

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
        ResultActions failedLoginRequest = mockMvc.perform(post("/login")
                .content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                          "message": "Bad Credentials",
                          "status": "UNAUTHORIZED"
                        }
                        """.trim())).andReturn();


        //4. The user tries to retrieve offers without a token, and the system returns error 401.
        //given, when
        ResultActions getOffersWithoutToken =
                mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        getOffersWithoutToken.andExpect(status().isForbidden());


        //5. The user registers with the data username=user, password=password, and receives a 201 response.
        //given, when
        ResultActions register = mockMvc.perform(post("/register").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        register.andExpect(status().isCreated());


        //6. The user tries to obtain a token again with the data username=user, password=password. The system returns a token with a 200 status.
        //given
        ResultActions registeredUserLogin = mockMvc.perform(post("/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        MvcResult mvcResult = registeredUserLogin.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto loginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        assertAll(
                () -> assertThat(loginResponseDto.username()).isEqualTo("user"),
                () -> assertThat(loginResponseDto.token()).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


        //7. Three offers appear on the external server.
        //given, when, then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(threeOffersBody())
                ));


        //8. The second retrieval from the external system is made, and three offers are retrieved (ids: 1111, 2222, 3333).
        //given, when
        List<OfferDto> offersAfterSecondRetrieval = offerScheduler.fetchOffersAndSave();
        //then
        assertThat(offersAfterSecondRetrieval).hasSize(3);


        //9. The authorized user sends a GET request to /offers and receives three offers from the database with a 200 status.
        //given, when
        ResultActions userMadeGetOffers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        String userMadeGetOffersJson = userMadeGetOffers.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<OfferDto> userMadeGetOffersList = objectMapper.readValue(userMadeGetOffersJson, new TypeReference<List<OfferDto>>() {
        });
        assertThat(userMadeGetOffersList).hasSize(3);


        //10. The authorized user sends a GET request to /offers/1111, and the system returns the offer with that ID with a 200 status.
        //given, when
        final String id1111 = offersAfterSecondRetrieval.get(0).id();
        ResultActions getOfferById = mockMvc.perform(get("/offers/" + id1111)
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON));
        getOfferById.andExpect(status().isOk());
        String getOfferJson = getOfferById.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferDto offerById = objectMapper.readValue(getOfferJson, OfferDto.class);
        //then
        assertThat(offerById.offerUrl()).isEqualTo(offersAfterSecondRetrieval.get(0).offerUrl());


        //11. The authorized user sends a GET request to /offers/4444, and the system returns a 404 status - not found.
        ResultActions getNotExistingOffer = mockMvc.perform(get("/offers/NotExistingId")
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON));
        getNotExistingOffer
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                          "message": "Offer not found",
                          "status": "NOT_FOUND"
                        }
                        """.trim())).andReturn();


        //12. User made POST request to /offers with empty body
        //given, when
        ResultActions postEmptyOffer = mockMvc.perform(post("/offers")
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));
        //then
        postEmptyOffer.andExpect(status().isBadRequest());


        //13. User made POST request to /offers with proper body
        //given, when
        ResultActions postOffer = mockMvc.perform(post("/offers")
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "companyName": "Company A",
                            "position": "position",
                            "salary": "10k - 18k PLN",
                            "offerUrl": "https://example.com/job/offer"
                        }
                           """.trim()));
        //then
        String createdOffer = postOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferDto parsedCreatedOffer = objectMapper.readValue(createdOffer, OfferDto.class);
        assertAll(
                () -> assertThat(parsedCreatedOffer.offerUrl()).isEqualTo("https://example.com/job/offer"),
                () -> assertThat(parsedCreatedOffer.companyName()).isEqualTo("Company A"),
                () -> assertThat(parsedCreatedOffer.salary()).isEqualTo("10k - 18k PLN"),
                () -> assertThat(parsedCreatedOffer.position()).isEqualTo("position")
        );


        //14. User made GET request to /offers and see if offer is added.
        //given, when
        ResultActions getOffersWithAddedOne = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + loginResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        String offersWithAddedOneJson = getOffersWithAddedOne.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<OfferDto> offersWithAddedOne = objectMapper.readValue(offersWithAddedOneJson, new TypeReference<List<OfferDto>>() {
        });
        assertThat(offersWithAddedOne).hasSize(4);

    }
}
