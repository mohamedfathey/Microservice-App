package com.programmingtechie.inventory_service.service;

import com.programmingtechie.inventory_service.dto.InventoryResponse;
import com.programmingtechie.inventory_service.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepo repo ;

    @Autowired
    public InventoryService(InventoryRepo repo) {
        this.repo = repo;
    }
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock (List<String> skuCode){
        return repo.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->{
                    InventoryResponse response = new InventoryResponse();
                    response.setSkuCode(inventory.getSkuCode());
                    response.setInStock(inventory.getQuantity() > 0);
                    return response ;
                }).collect(Collectors.toList());
    }
}
