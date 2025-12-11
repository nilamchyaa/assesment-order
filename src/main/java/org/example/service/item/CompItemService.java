package org.example.service.item;

import jakarta.transaction.Transactional;
import org.example.constants.WorkflowStatus;
import org.example.dto.GeneralResponse;
import org.example.dto.ItemStockDto;
import org.example.dto.NewItemRequest;
import org.example.dto.UpdateItemRequest;
import org.example.entity.Item;
import org.example.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompItemService {
    @Autowired private ItemService itemService;
    @Autowired private InventoryService inventoryService;

    public GeneralResponse<List<ItemStockDto>> getAllItem(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ItemStockDto> results = itemService.getAllItem(pageable);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved all item data.",
                results.getTotalElements(),
                (long) results.getNumberOfElements(),
                (long) page,
                (long) results.getTotalPages(),
                results.getContent());
    }

    public GeneralResponse<ItemStockDto> getById(Long id){
        Item item = itemService.getItemByIdOrThrow(id);
        Long remainStock = inventoryService.getTotalRemain(item.getId());

        ItemStockDto itemStockDto = new ItemStockDto();
        itemStockDto.setId(item.getId());
        itemStockDto.setName(item.getName());
        itemStockDto.setPrice(item.getPrice());
        itemStockDto.setRemain_stock(remainStock);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved item data.",
                itemStockDto);
    }

    @Transactional
    public GeneralResponse createNewItem(NewItemRequest request){

        Item item = new Item();
        item.setName(request.getName());
        item.setPrice(Double.parseDouble(request.getPrice()));
        itemService.saveItem(item);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully added new item"
        );
    }

    @Transactional
    public GeneralResponse updateItem(Long id, UpdateItemRequest request){

        Item item = itemService.getItemByIdOrThrow(id);
        String name =  (request.getName() != null ? request.getName() : item.getName());
        Double price =  (request.getPrice() != null ? Double.parseDouble(request.getPrice()) : item.getPrice());

        item.setName(name);
        item.setPrice(price);
        itemService.saveItem(item);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully updated item"
        );
    }

    @Transactional
    public GeneralResponse deleteItem(Long id){
        Item item = itemService.getItemByIdOrThrow(id);
        itemService.deleteItem(item);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully to remove item"
        );
    }

}
