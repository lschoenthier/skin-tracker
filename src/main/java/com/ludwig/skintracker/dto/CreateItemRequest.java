package com.ludwig.skintracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateItemRequest {
    @NotBlank
    private String marketHashName;
    @PositiveOrZero
    private Double minFloat;
    @PositiveOrZero
    private Double maxFloat;
    private Integer paintSeed;

    public String getMarketHashName() {
        return marketHashName;
    }

    public void setMarketHashName(String marketHashName) {
        this.marketHashName = marketHashName;
    }

    public Double getMinFloat() {
        return minFloat;
    }

    public void setMinFloat(Double minFloat) {
        this.minFloat = minFloat;
    }

    public Double getMaxFloat() {
        return maxFloat;
    }

    public void setMaxFloat(Double maxFloat) {
        this.maxFloat = maxFloat;
    }

    public Integer getPaintSeed() {
        return paintSeed;
    }

    public void setPaintSeed(Integer paintSeed) {
        this.paintSeed = paintSeed;
    }
}
