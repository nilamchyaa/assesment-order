package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.GeneralResponse;
import org.example.dto.ItemStockDto;
import org.example.dto.NewItemRequest;
import org.example.dto.UpdateItemRequest;
import org.example.service.item.CompItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired private CompItemService compItemService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<ItemStockDto>>> getAllItem(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(compItemService.getAllItem(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<ItemStockDto>> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compItemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createNewItem(@Valid @RequestBody NewItemRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compItemService.createNewItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateItem(@PathVariable Long id, @Valid @RequestBody UpdateItemRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compItemService.updateItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteItem(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compItemService.deleteItem(id));
    }
}