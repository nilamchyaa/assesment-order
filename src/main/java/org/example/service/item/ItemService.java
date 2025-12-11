package org.example.service.item;

import org.example.dto.ItemStockDto;
import org.example.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    Page<ItemStockDto> getAllItem(Pageable pageable);
    void saveItem(Item item);
    void deleteItem(Item item);
    Item getItemByIdOrThrow(Long id);
}
