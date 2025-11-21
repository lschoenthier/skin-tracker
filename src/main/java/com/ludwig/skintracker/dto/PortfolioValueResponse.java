package com.ludwig.skintracker.dto;

public class PortfolioValueResponse {
    private Double totalUsd;

    public PortfolioValueResponse() {
    }

    public PortfolioValueResponse(Double totalUsd) {
        this.totalUsd = totalUsd;
    }

    public Double getTotalUsd() {
        return totalUsd;
    }

    public void setTotalUsd(Double totalUsd) {
        this.totalUsd = totalUsd;
    }
}