package org.example.repository;

import org.example.dto.ItemStockDto;
import org.example.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
    SELECT new org.example.dto.ItemStockDto(
        i.id,
        i.name,
        i.price,
        COALESCE(SUM(
            CASE
                WHEN inv.type = 'T' THEN inv.qty
                WHEN inv.type = 'W' THEN -inv.qty
                ELSE 0
            END
        ), 0)
    )
    FROM Item i
    LEFT JOIN Inventory inv ON inv.item.id = i.id
    GROUP BY i.id, i.name, i.price
    """)
    Page<ItemStockDto> getAllItem(Pageable pageable);


}

