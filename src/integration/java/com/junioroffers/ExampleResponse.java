package com.junioroffers;

public interface ExampleResponse {


    default String threeOffersBody() {
        return """
                [
                {
                    "title": "Backend Developer",
                    "company": "Company X",
                    "salary": "10k - 18k PLN",
                    "offerUrl": "https://example.com/job/offer-4"
                },
                {
                    "title": "Data Scientist",
                    "company": "Company Y",
                    "salary": "12k - 20k PLN",
                    "offerUrl": "https://example.com/job/offer-5"
                },
                {
                    "title": "Frontend Developer",
                    "company": "Company Z",
                    "salary": "9k - 15k PLN",
                    "offerUrl": "https://example.com/job/offer-6"
                }
                ]
                """.trim();
    }

    default String zeroOffersBody() {
        return "[]";
    }
}
