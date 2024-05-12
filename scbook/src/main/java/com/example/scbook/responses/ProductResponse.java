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
    private Long id;
    private String name;
    private Float price;
    private String url;
    private String description;
    @JsonProperty("units_in_stock")
    private Integer unitsInStock;
    @JsonProperty("category_id")
    private Long categoryId;
    private String author;
    private Integer sold;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static ProductResponse fromProduct(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .url(product.getUrl())
                .unitsInStock(product.getUnitsInStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .description(product.getDescription())
                .author(product.getAuthor())
                .sold(product.getSold())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
