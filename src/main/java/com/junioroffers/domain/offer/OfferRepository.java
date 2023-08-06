package com.junioroffers.domain.offer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface OfferRepository {

    boolean existsOfferByUrl(String offerUrl);

    Optional<Offer> findOfferByUrl(String offerUrl);

    List<Offer> saveAll(List<Offer> offers);

    List<Offer> findAll();

    Optional<Offer> findById(String id);

    Offer save(Offer offer);
}
