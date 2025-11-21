package com.ludwig.skintracker.dto;

public class LatestPriceResponse {
    private Long itemId;
    private Double priceUsd;

    public LatestPriceResponse() {
    }

    public LatestPriceResponse(Long itemId, Double priceUsd) {
        this.itemId = itemId;
        this.priceUsd = priceUsd;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }
}
