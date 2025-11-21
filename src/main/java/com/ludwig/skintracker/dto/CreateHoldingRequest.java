package com.ludwig.skintracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class CreateHoldingRequest {
    @NotNull
    private Long itemId;
    @Positive
    private Integer amount;
    @Min(0)
    private Integer buyPriceCents;
    private Instant buyTs;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBuyPriceCents() {
        return buyPriceCents;
    }

    public void setBuyPriceCents(Integer buyPriceCents) {
        this.buyPriceCents = buyPriceCents;
    }

    public Instant getBuyTs() {
        return buyTs;
    }

    public void setBuyTs(Instant buyTs) {
        this.buyTs = buyTs;
    }
}
