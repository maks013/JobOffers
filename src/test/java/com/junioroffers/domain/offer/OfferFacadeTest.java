package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.JobOfferResponse;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.exception.OfferNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OfferFacadeTest {

    @Test
    void should_save_4_offers_when_there_are_no_offers_in_database() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeConfigForTests();
        //when
        offerFacade.saveOffer(new OfferRequestDto("company", "abc", "abcdefg", "example1.com" ));
        offerFacade.saveOffer(new OfferRequestDto("company", "abc", "abcdefg", "example2.com" ));
        offerFacade.saveOffer(new OfferRequestDto("company", "abc", "abcdefg", "example3.com" ));
        offerFacade.saveOffer(new OfferRequestDto("company", "abc", "abcdefg", "example4.com" ));
        //then
        assertEquals(offerFacade.findAllOffers().size(), 4);
    }

    @Test
    void should_save_only_2_offers_when_repository_had_3_added_with_offer_urls() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of(
                new JobOfferResponse("Java Developer", "Example1", "9000PLN", "https://example.com/offer1" ),
                new JobOfferResponse("Software Engineer", "Example2", "9500PLN", "https://example.com/offer2" ),
                new JobOfferResponse("Data Analyst", "Example3", "10000PLN", "https://example.com/offer3" ),
                new JobOfferResponse("Title1", "Example4", "10000PLN", "https://example.com/offer4" ),
                new JobOfferResponse("Title2", "Example5", "10000PLN", "https://example.com/offer5" )
        )).offerFacadeConfigForTests();
        //when
        offerFacade.saveOffer(new OfferRequestDto("Java Developer", "Example1", "9000PLN", "https://example.com/offer1" ));
        offerFacade.saveOffer(new OfferRequestDto("Software Engineer", "Example2", "9500PLN", "https://example.com/offer2" ));
        offerFacade.saveOffer(new OfferRequestDto("Data Analyst", "Example3", "10000PLN", "https://example.com/offer3" ));

        List<OfferDto> response = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertAll(
                () -> assertEquals(response.get(0).offerUrl(), "https://example.com/offer4" ),
                () -> assertEquals(response.get(1).offerUrl(), "https://example.com/offer5" )
        );
    }

    @Test
    void should_throw_duplicate_key_exception_when_offer_with_url_exists() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeConfigForTests();
        OfferDto offerDto = offerFacade.saveOffer(
                new OfferRequestDto("company", "abc", "abcdefg", "example.com" ));
        //when
        OfferRequestDto offerRequestDto = new OfferRequestDto(
                "company", "abc2", "abcdefg2", "example.com" );
        //then
        assertThrows(DuplicateKeyException.class, () -> offerFacade.saveOffer(offerRequestDto));
    }

    @Test
    void should_throw_not_found_exception_when_offer_not_found() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeConfigForTests();
        //when
        //then
        assertThrows(OfferNotFoundException.class, () -> offerFacade.findOfferById("idExample" ));
    }

    @Test
    void should_fetch_from_remote_and_save_all_offers_when_repository_is_empty() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeConfigForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();
        // when
        List<OfferDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertEquals(result.size(), 5);
    }

    @Test
    void should_find_offer_byId_when_offer_is_saved() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeConfigForTests();
        OfferDto offerDto = offerFacade.saveOffer(new OfferRequestDto("company", "abc", "abcdefg", "example1.com" ));
        //when
        OfferDto offerById = offerFacade.findOfferById(offerDto.id());
        //then
        assertEquals(offerDto, offerById);
    }
}
