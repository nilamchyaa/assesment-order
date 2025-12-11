package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewInventoryRequest {
    @NotNull(message = "{item_id.not.null}")
    @NotBlank(message = "{item_id.not.blank}")
    @Pattern(regexp = "\\d+", message = "{item_id.must.numbers}")
    private String item_id;

    private Integer qty;

    @NotNull(message = "{type.not.null}")
    @NotBlank(message = "{type.not.blank}")
    private String type;
}
