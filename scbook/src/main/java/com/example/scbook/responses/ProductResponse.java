package com.example.scbook.responses;

import com.example.scbook.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String name;
    private Float price;
    private String url;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
    private String author;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static ProductResponse fromProduct(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .url(product.getUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .description(product.getDescription())
                .author(product.getAuthor())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
