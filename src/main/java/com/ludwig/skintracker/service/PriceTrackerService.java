package com.ludwig.skintracker.service;

import com.ludwig.skintracker.model.Item;
import com.ludwig.skintracker.model.PriceHistory;
import com.ludwig.skintracker.repo.ItemRepo;
import com.ludwig.skintracker.repo.PriceHistoryRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Locale;

@Service
public class PriceTrackerService {

    private final ItemRepo items;
    private final PriceHistoryRepo hist;
    private final CsFloatClient api;

    public PriceTrackerService(ItemRepo items, PriceHistoryRepo hist, CsFloatClient api) {
        this.items = items; this.hist = hist; this.api = api;
    }

    // alle 30 Minuten (volle/halbe Stunde)
    @Scheduled(cron = "0 */30 * * * *")
    public void pollAll() {
        for (Item it : items.findAll()) {
            try {
                Integer price = api.fetchLowestPrice(
                        it.getMarketHashName(), it.getMinFloat(), it.getMaxFloat(), it.getPaintSeed());

                if (price == null) {
                    log("Kein Listing: %s", it.getMarketHashName());
                    continue;
                }

                PriceHistory h = new PriceHistory();
                h.setItem(it);
                h.setTs(Instant.now().getEpochSecond());
                h.setLowestPriceCents(price);
                hist.save(h);

                log("%s: %.2f $", it.getMarketHashName(), price / 100.0);
            } catch (Exception e) {
                log("Fehler bei %s: %s", it.getMarketHashName(), e.getMessage());
            }
        }
    }

    private static void log(String fmt, Object... args) {
        System.out.println(String.format(Locale.ROOT, fmt, args));
    }
}
