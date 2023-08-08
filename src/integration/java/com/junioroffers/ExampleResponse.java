package com.junioroffers;

public interface ExampleResponse {

    default String zeroOffersBody() {
        return "[]";
    }
}
