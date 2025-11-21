package com.ludwig.skintracker.repo;

import com.ludwig.skintracker.model.Holding;
import com.ludwig.skintracker.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingRepo extends JpaRepository<Holding, Long> {
    List<Holding> findByActiveTrue();

    List<Holding> findByItemAndActiveTrue(Item item);
}
