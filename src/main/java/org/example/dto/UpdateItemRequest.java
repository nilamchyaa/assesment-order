package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class UpdateItemRequest {
    @Size(max = 200, message = "{name.max.length}")
    private String name;

    @Pattern(regexp = "\\d.+", message = "{price.must.numbers}")
    @Size(max = 15, message = "{price.max.length}")
    private String price;
}
