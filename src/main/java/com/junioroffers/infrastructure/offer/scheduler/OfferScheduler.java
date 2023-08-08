package com.junioroffers.infrastructure.offer.scheduler;


import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@AllArgsConstructor
public class OfferScheduler {

    private final OfferFacade facade;

    @Scheduled(fixedDelayString = "${offer.scheduler.delay}")
    public List<OfferDto> fetchOffersAndSave(){
        log.info("Scheduler started fetching offers");
        final List<OfferDto> addedOffers = facade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Added new offers {}",addedOffers.size());
        return addedOffers;
    }
}
