package org.example.service.item;

import org.example.dto.ItemStockDto;
import org.example.entity.Item;
import org.example.exception.ItemNotFoundException;
import org.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired private ItemRepository itemRepository;
    @Autowired private MessageSource messageSource;

    public Item findById(Long id){
        Optional<Item> opt = itemRepository.findById(id);
        Item item = opt.isPresent() ? opt.get() : null;

        return item;
    }

    @Override
    public Page<ItemStockDto> getAllItem(Pageable pageable){
        return itemRepository.getAllItem(pageable);
    }

    @Override
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

    @Override
    public Item getItemByIdOrThrow(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ItemNotFoundException(
                                messageSource.getMessage("item.not.found", null, Locale.ROOT)
                        )
                );
        return item;
    }

}
