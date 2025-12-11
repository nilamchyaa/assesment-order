package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewOrderDetailRequest {
    @NotNull(message = "{item_id.not.null}")
    @NotBlank(message = "{item_id.not.blank}")
    @Pattern(regexp = "\\d.+", message = "{item_id.must.numbers}")
    private String item_id;

    private Integer qty;
}