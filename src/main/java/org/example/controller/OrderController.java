package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.service.order.CompOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private CompOrderService compOrderService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<OrderDto>>> getAllOrders(@RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.CREATED).body(compOrderService.getAllOrders(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<OrderDto>> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compOrderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createNewOrder(@Valid @RequestBody NewOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compOrderService.saveOrder(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(compOrderService.updateOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteInventory(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(compOrderService.deleteOrder(id));
    }
}