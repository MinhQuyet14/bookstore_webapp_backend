package com.example.scbook.responses;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class OrderListResponse {
    private List<OrderResponse> orders;
    private int totalPages;
}
