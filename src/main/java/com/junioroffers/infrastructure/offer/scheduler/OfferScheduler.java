package com.junioroffers.infrastructure.offer.scheduler;


import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class OfferScheduler {

    private final OfferFacade facade;

    @Scheduled(fixedDelayString = "${offer.scheduler.delay}")
    public void fetchOffersAndSave(){
        log.info("Scheduler started fetching offers");
        List<OfferDto> offers = facade.fetchAllOffersAndSaveAllIfNotExists();
        log.info(offers);
    }
}
