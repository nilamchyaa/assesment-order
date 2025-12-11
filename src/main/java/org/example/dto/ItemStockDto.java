package org.example.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemStockDto {

    private Long id;
    private String name;
    private Double price;
    private Long remain_stock;
}
