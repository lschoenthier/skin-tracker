package com.ludwig.skintracker.repo;

import com.ludwig.skintracker.model.Item;
import com.ludwig.skintracker.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepo extends JpaRepository<PriceHistory, Long> {
    Optional<PriceHistory> findTopByItemOrderByTsDesc(Item item);

    List<PriceHistory> findByItemOrderByTsDesc(Item item);
}
