package com.example.scbook.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoldProductListResponse {
    private List<SoldProductResponse> products;
    @JsonProperty("total_pages")
    private int totalPages;
}
