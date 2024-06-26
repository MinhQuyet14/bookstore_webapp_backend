package com.example.scbook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private Float price;
    private String url;
    private String description;
    @JsonProperty("units_in_stock")
    private Integer unitsInStock;
    private Integer sold;
/*    @JsonProperty("category_id")
    private Long categoryId;

 */
    private String author;
}
