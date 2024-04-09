package com.example.scbook.responses;

import com.example.scbook.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;
    private Float price;
    @JsonProperty("number_of_products")
    private int numberOfProduct;
    @JsonProperty("total_money")
    private Float totalMoney;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProduct(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }
}
