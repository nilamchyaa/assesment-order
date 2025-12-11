package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewItemRequest {
    @NotNull(message = "{name.not.null}")
    @NotBlank(message = "{name.not.blank}")
    @Size(max = 200, message = "{name.max.length}")
    private String name;

    @NotNull(message = "{price.not.null}")
    @NotBlank(message = "{price.not.blank}")
    @Pattern(regexp = "\\d.+", message = "{price.must.numbers}")
    @Size(max = 15, message = "{price.max.length}")
    private String price;
}
