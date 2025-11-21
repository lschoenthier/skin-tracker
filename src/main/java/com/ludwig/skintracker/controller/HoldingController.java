package com.ludwig.skintracker.controller;

import com.ludwig.skintracker.dto.CreateHoldingRequest;
import com.ludwig.skintracker.dto.PortfolioValueResponse;
import com.ludwig.skintracker.dto.SellHoldingRequest;
import com.ludwig.skintracker.model.Holding;
import com.ludwig.skintracker.model.Item;
import com.ludwig.skintracker.model.PriceHistory;
import com.ludwig.skintracker.repo.HoldingRepo;
import com.ludwig.skintracker.repo.ItemRepo;
import com.ludwig.skintracker.repo.PriceHistoryRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingRepo holdings;
    private final ItemRepo items;
    private final PriceHistoryRepo prices;

    public HoldingController(HoldingRepo holdings, ItemRepo items, PriceHistoryRepo prices) {
        this.holdings = holdings;
        this.items = items;
        this.prices = prices;
    }

    @GetMapping
    public List<Holding> all() {
        return holdings.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Holding buy(@Valid @RequestBody CreateHoldingRequest body) {
        Item it = items.findById(body.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        Holding h = new Holding();
        h.setItem(it);
        h.setActive(true);
        h.setAmount(body.getAmount());
        h.setBuyPriceCents(body.getBuyPriceCents());
        h.setBuyTs(body.getBuyTs() != null ? body.getBuyTs() : Instant.now());
        return holdings.save(h);
    }

    @PutMapping("/{id}/sell")
    public Holding sell(@PathVariable Long id, @Valid @RequestBody SellHoldingRequest body) {
        Holding h = holdings.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Holding not found"));
        h.setSellPriceCents(body.getSellPriceCents());
        h.setSellTs(body.getSellTs() != null ? body.getSellTs() : Instant.now());
        h.setActive(false);
        return holdings.save(h);
    }

    @GetMapping("/value")
    public PortfolioValueResponse portfolioValueUsd() {
        double sum = 0.0;
        for (Holding h : holdings.findByActiveTrue()) {
            PriceHistory last = prices.findTopByItemOrderByTsDesc(h.getItem()).orElse(null);
            if (last == null) continue;
            sum += (last.getLowestPriceCents() / 100.0) * h.getAmount();
        }
        return new PortfolioValueResponse(sum);
    }
}
