package com.ludwig.skintracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    private boolean active; // im Inv oder nicht

    private int amount;

    private Integer buyPriceCents;
    private Integer sellPriceCents;

    private Instant buyTs;
    private Instant sellTs;
}
