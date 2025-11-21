package com.ludwig.skintracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String marketHashName;

    private Double minFloat;
    private Double maxFloat;
    private Integer paintSeed;
}
