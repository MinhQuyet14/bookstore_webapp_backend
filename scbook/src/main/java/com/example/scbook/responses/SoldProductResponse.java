package com.example.scbook.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SoldProductResponse {

    Long getId();
    String getName();
    Float getPrice();
    String getUrl();
    String getDescription();
    @JsonProperty("category_id")
    Long getCategoryId();
    String getAuthor();
    @JsonProperty("number_of_products")
    Integer getTotalProducts();
}
