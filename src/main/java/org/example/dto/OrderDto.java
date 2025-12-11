package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String orderNo;
    private Double totalPrice;
    private List<OrderDetailDto> detail;
}

