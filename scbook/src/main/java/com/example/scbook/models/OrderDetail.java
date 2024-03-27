package com.example.scbook.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Float price;

    @Column(name = "number_of_products")
    private int numberOfProducts;
    @Column(name = "total_money")
    private Integer totalMoney;
}
