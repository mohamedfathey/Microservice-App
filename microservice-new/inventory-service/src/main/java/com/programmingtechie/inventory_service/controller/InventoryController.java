package com.programmingtechie.inventory_service.controller;

import com.programmingtechie.inventory_service.dto.InventoryResponse;
import com.programmingtechie.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service ;

    @Autowired
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isOnStock (@RequestParam List<String> skuCode){
        return service.isInStock(skuCode);
    }

    @GetMapping("/done")
    public String test(){
        return "done" ;
    }

}
