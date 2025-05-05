package com.programmingtechie.inventory_service.repo;

import com.programmingtechie.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
//    Optional<Inventory>findBySkuCode(String skuCode);

    Optional<Inventory> findBySkuCodeIn(List<String> skuCode);
}
