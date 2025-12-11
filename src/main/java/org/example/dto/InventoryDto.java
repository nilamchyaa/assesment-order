package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDto {

    private Long id;
    private ItemDto item;
    private Integer qty;
    private String type;
}

