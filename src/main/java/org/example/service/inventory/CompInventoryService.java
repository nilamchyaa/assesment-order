package org.example.service.inventory;

import jakarta.transaction.Transactional;
import org.example.constants.ItemType;
import org.example.constants.WorkflowStatus;
import org.example.dto.*;
import org.example.entity.Inventory;
import org.example.entity.Item;
import org.example.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompInventoryService {
    @Autowired private ItemService itemService;
    @Autowired private InventoryService inventoryService;

    public GeneralResponse<List<InventoryDto>> getAllInventory(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Inventory> pageResult= inventoryService.getAllInventory(pageable);

        List<InventoryDto> dtoList = pageResult.getContent().stream()
                .map(inv -> {
                    Item item = inv.getItem();
                    ItemDto itemDto = new ItemDto(item.getId(), item.getName(), item.getPrice());
                    return new InventoryDto(inv.getId(), itemDto, inv.getQty(), inv.getType().name());
                })
                .toList();

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved all inventory data.",
                pageResult.getTotalElements(),
                (long) pageResult.getNumberOfElements(),
                (long) page,
                (long) pageResult.getTotalPages(),
                dtoList);
    }

    public GeneralResponse<InventoryDto> getById(Long id){
        Inventory inventory = inventoryService.getInventoryByIdOrThrow(id);
        ItemDto itemDto = new ItemDto(inventory.getItem().getId(),
                inventory.getItem().getName(), inventory.getItem().getPrice());

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setItem(itemDto);
        inventoryDto.setQty(inventory.getQty());
        inventoryDto.setType(inventory.getType().name());

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved inventory data.",
                inventoryDto);
    }

    @Transactional
    public GeneralResponse createNewInventory(NewInventoryRequest request){

        Item item = itemService.getItemByIdOrThrow(Long.parseLong(request.getItem_id()));
        Long remainStock = getTotalRemain(item.getId());
        if(request.getType().toUpperCase().equals("W")){
            if(request.getQty().longValue() > remainStock) {
                throw new RuntimeException("Stok tidak cukup. Stock tersisa : " + remainStock);
            }
        }

        Inventory inventory = new Inventory();
        inventory.setItem(item);
        inventory.setQty(request.getQty());
        inventory.setType(ItemType.valueOf(request.getType().toUpperCase()));
        inventoryService.saveInventory(inventory);

        return new GeneralResponse(WorkflowStatus.SUCCESS,
                "Successfully added new inventory data");
    }

    public Long getTotalRemain(Long itemId){
        return inventoryService.getTotalRemain(itemId);
    }

    @Transactional
    public GeneralResponse updateInventory(Long id, UpdateInventoryRequest request){

        Inventory inventory = inventoryService.getInventoryByIdOrThrow(id);
        String item_id =  (request.getItem_id() != null ? request.getItem_id() : inventory.getItem().getId().toString());
        Integer qty =  (request.getQty() != null ? request.getQty() : inventory.getQty());
        String type = (request.getType().toUpperCase() != null ? request.getType().toUpperCase() : inventory.getType().name());

        Item item = itemService.getItemByIdOrThrow(Long.parseLong(item_id));
        Long remainStock = getTotalRemain(item.getId());
        if(request.getType().toUpperCase().equals("W")){
            if(item_id.equals(inventory.getItem().getId())){
                Long newRemainStock = remainStock - inventory.getQty().longValue();
                if(request.getQty().longValue() > newRemainStock) {
                    throw new RuntimeException("Stok tidak cukup. Stock tersisa : " + newRemainStock);
                }
            }else{
                if(request.getQty().longValue() > remainStock) {
                    throw new RuntimeException("Stok tidak cukup. Stock tersisa : " + remainStock);
                }
            }
        }

        inventory.setItem(item);
        inventory.setQty(qty);
        inventory.setType(ItemType.valueOf(type));
        inventoryService.saveInventory(inventory);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully updated inventory data"
        );
    }

    @Transactional
    public GeneralResponse deleteInventory(Long id){
        Inventory inventory = inventoryService.getInventoryByIdOrThrow(id);
        inventoryService.deleteInventory(inventory);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully to remove inventory data"
        );
    }
}
