package org.example.service.inventory;

import org.example.entity.Inventory;
import org.example.entity.Item;
import org.example.exception.ItemNotFoundException;
import org.example.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private MessageSource messageSource;

    @Override
    public Page<Inventory> getAllInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public void saveInventory(Inventory inventory){
        inventoryRepository.save(inventory);
    }

    @Override
    public Long getTotalRemain(Long itemId){
        return inventoryRepository.getTotalRemain(itemId);
    }

    @Override
    public Inventory getInventoryByIdOrThrow(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ItemNotFoundException(
                                messageSource.getMessage("inventory.not.found", null, Locale.ROOT)
                        )
                );
        return inventory;
    }

    @Override
    public void deleteInventory(Inventory inventory){
        inventoryRepository.delete(inventory);
    }
}
