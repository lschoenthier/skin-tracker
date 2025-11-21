package com.ludwig.skintracker.controller;

import com.ludwig.skintracker.dto.CreateItemRequest;
import com.ludwig.skintracker.dto.LatestPriceResponse;
import com.ludwig.skintracker.model.Item;
import com.ludwig.skintracker.model.PriceHistory;
import com.ludwig.skintracker.repo.ItemRepo;
import com.ludwig.skintracker.repo.PriceHistoryRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemRepo items;
    private final PriceHistoryRepo prices;

    public ItemController(ItemRepo items, PriceHistoryRepo prices) {
        this.items = items;
        this.prices = prices;
    }

    @GetMapping
    public List<Item> all() {
        return items.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@Valid @RequestBody CreateItemRequest body) {
        return items.findByMarketHashName(body.getMarketHashName())
                .orElseGet(() -> {
                    Item it = new Item();
                    it.setMarketHashName(body.getMarketHashName());
                    it.setMinFloat(it.getMinFloat());
                    it.setMaxFloat(body.getMaxFloat());
                    it.setPaintSeed(body.getPaintSeed());
                    return items.save(it);
                });
    }

    @GetMapping("/{id}/history")
    public List<PriceHistory> history(@PathVariable Long id) {
        Item it = items.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return prices.findByItemOrderByTsDesc(it);
    }

    @GetMapping("/{id}/latest")
    public LatestPriceResponse latestUsd(@PathVariable Long id) {
        Item it = items.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Double usd = prices.findTopByItemOrderByTsDesc(it)
                .map(ph -> ph.getLowestPriceCents() / 100.0)
                .orElse(null);
        return new LatestPriceResponse(id, usd);
    }
}
