package org.example.service.inventory;

import org.example.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    Page<Inventory> getAllInventory(Pageable pageable);
    void saveInventory(Inventory inventory);
    Long getTotalRemain(Long itemId);
    Inventory getInventoryByIdOrThrow(Long id);
    void deleteInventory(Inventory inventory);
}
