package com.example.scbook.responses;

import com.example.scbook.models.Order;
import com.example.scbook.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private String address;
    private String note;
    @JsonProperty("order_date")
    private LocalDate orderDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_money")
    private Float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("order_details")
    private List<OrderDetail> orderDetails;

    public static OrderResponse fromOrder(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .fullName(order.getFullName())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .orderDetails(order.getOrderDetails())
                .build();
    }
}
