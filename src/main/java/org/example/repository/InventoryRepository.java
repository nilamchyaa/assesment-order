package org.example.repository;

import org.example.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
            SELECT COALESCE(SUM(
                CASE 
                    WHEN i.type = 'T' THEN i.qty
                    WHEN i.type = 'W' THEN -i.qty
                    ELSE 0
                END
            ), 0)
            FROM Inventory i
            WHERE i.item.id = :itemId
            """)
    Long getTotalRemain(@Param("itemId") Long itemId);

}

