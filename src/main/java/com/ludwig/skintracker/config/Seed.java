package com.ludwig.skintracker.config;

import com.ludwig.skintracker.model.Item;
import com.ludwig.skintracker.repo.ItemRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("seed")
public class Seed implements CommandLineRunner {
    private final ItemRepo items;
    public Seed(ItemRepo items){ this.items = items; }

    @Override public void run(String... args) {
        items.findByMarketHashName("M4A4 | Hellfire (Factory New)").orElseGet(() -> {
            Item it = new Item();
            it.setMarketHashName("M4A4 | Hellfire (Factory New)");
            it.setMinFloat(0.0); it.setMaxFloat(0.07); // FN-Grenze
            return items.save(it);
        });
    }
}
