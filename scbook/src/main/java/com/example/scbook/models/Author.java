package com.example.scbook.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
