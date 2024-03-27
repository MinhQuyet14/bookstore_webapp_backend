package com.example.scbook.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_account")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String provider;
    @Column(name = "provider_id", length = 50, nullable = false)
    private String providerId;

    private String email;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
