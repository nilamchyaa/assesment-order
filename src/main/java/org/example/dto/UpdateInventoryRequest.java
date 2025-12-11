package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateInventoryRequest {
    @Pattern(regexp = "\\d+", message = "{item_id.must.numbers}")
    private String item_id;
    private Integer qty;
    private String type;
}
