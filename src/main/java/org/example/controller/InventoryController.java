package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.service.inventory.CompInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired private CompInventoryService compInventoryService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<InventoryDto>>> getAllInventory(@RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.CREATED).body(compInventoryService.getAllInventory(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<InventoryDto>> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compInventoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createNewInventory(@Valid @RequestBody NewInventoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compInventoryService.createNewInventory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateInventory(@PathVariable Long id, @Valid @RequestBody UpdateInventoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compInventoryService.updateInventory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteInventory(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compInventoryService.deleteInventory(id));
    }
}