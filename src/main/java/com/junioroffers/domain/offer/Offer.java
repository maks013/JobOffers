package com.junioroffers.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("offers")
record Offer(@Id
             String id,
             @Field("company") String companyName,
             @Field("position") String position,
             @Field("salary") String salary,
             @Field("url") String offerUrl) {
    @Builder public Offer{}
}
