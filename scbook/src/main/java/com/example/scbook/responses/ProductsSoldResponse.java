package com.example.scbook.responses;

import com.example.scbook.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsSoldResponse {
    private Long id;
    private String name;
    private Float price;
    private String url;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
    private String author;
    @JsonProperty("number_of_products")
    private int numberOfProducts;

}
