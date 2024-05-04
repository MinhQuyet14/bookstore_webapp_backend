package com.example.scbook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "fullname")
    private String fullName;
    private String email;
    @Column(name = "phone_number", nullable = false)
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    private String note;
    @Column(name = "order_date")
    @JsonProperty("order_date")
    private LocalDate orderDate;
    @Column(name = "status")
    private String status;
    @Column(name = "total_money")
    @JsonProperty("total_money")
    private Float totalMoney;
    @Column(name = "shipping_method")
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @Column(name = "shipping_address")
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @Column(name = "shipping_date")
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    @Column(name = "tracking_number")
    @JsonProperty("tracking_number")
    private String trackingNumber;
    @Column(name = "payment_method")
    @JsonProperty("payment_method")
    private String paymentMethod;
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
