package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.constants.ItemType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer qty;

    @Enumerated(EnumType.STRING)
    private ItemType type;
}
