package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewOrderRequest {
    @NotNull(message = "{order_no.not.null}")
    @NotBlank(message = "{order_no.not.blank}")
    private String order_no;

    private List<NewOrderDetailRequest> details;
}
