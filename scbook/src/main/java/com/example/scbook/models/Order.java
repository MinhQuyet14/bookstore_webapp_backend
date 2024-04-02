package com.example.scbook.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    private String phoneNumber;
    private String address;
    private String note;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "status")
    private String status;
    @Column(name = "total_money")
    private Integer totalMoney;
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "shipping_date")
    private Date shippingDate;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "active")
    private boolean active;
}
