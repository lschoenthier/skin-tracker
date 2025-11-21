package com.ludwig.skintracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    @Column(nullable = false)
    private Long ts;

    @Column(nullable = false)
    private Integer lowestPriceCents;
}
