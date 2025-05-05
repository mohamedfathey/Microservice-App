package com.programmingtechie.inventory_service.util;

import com.programmingtechie.inventory_service.entity.Inventory;
import com.programmingtechie.inventory_service.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final InventoryRepo inventoryRepository;

    @Autowired
    public DataLoader(InventoryRepo inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Inventory inventory = new Inventory();
        inventory.setSkuCode("iphone_13");
        inventory.setQuantity(100);

        Inventory inventory1 = new Inventory();
        inventory1.setSkuCode("iphone_13_red");
        inventory1.setQuantity(0);

        inventoryRepository.save(inventory);
        inventoryRepository.save(inventory1);
    }


}
