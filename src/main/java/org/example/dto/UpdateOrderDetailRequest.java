package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDetailRequest {
    @Pattern(regexp = "\\d.+", message = "{id.must.numbers}")
    private Long id;

    @NotNull(message = "{item_id.not.null}")
    @NotBlank(message = "{item_id.not.blank}")
    @Pattern(regexp = "\\d.+", message = "{item_id.must.numbers}")
    private Long item_id;

    private Integer qty;
}