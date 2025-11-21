package com.ludwig.skintracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class SellHoldingRequest {
    @NotNull
    @Min(0)
    private Integer sellPriceCents;
    private Instant sellTs;

    public Integer getSellPriceCents() {
        return sellPriceCents;
    }

    public void setSellPriceCents(Integer sellPriceCents) {
        this.sellPriceCents = sellPriceCents;
    }

    public Instant getSellTs() {
        return sellTs;
    }

    public void setSellTs(Instant sellTs) {
        this.sellTs = sellTs;
    }
}
