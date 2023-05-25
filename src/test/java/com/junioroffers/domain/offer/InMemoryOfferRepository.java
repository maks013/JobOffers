package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.exception.OfferDuplicationException;

import java.util.*;

class InMemoryOfferRepository implements OfferRepository {

    Map<String, Offer> inMemoryDatabase = new HashMap<>();

    @Override
    public boolean existsOfferByUrl(String offerUrl) {
        long count = inMemoryDatabase.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public Optional<Offer> findOfferByUrl(String offerUrl) {
        return Optional.of(inMemoryDatabase.get(offerUrl));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public List<Offer> findAll() {
        return inMemoryDatabase.values().stream().toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public Offer save(Offer offer) {
        if(inMemoryDatabase.values().stream().anyMatch(offer1 -> offer1.offerUrl().equals(offer.offerUrl()))){
            throw new OfferDuplicationException();
        }

        String id = UUID.randomUUID().toString();
        Offer offerToSave = new Offer(
                id,
                offer.companyName(),
                offer.position(),
                offer.salary(),
                offer.offerUrl()
        );
        inMemoryDatabase.put(id, offerToSave);
        return offerToSave;
    }
}
