package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constants.WorkflowStatus;

import java.lang.reflect.Constructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeneralResponse<T> {
    private WorkflowStatus status;
    private String message;
    private Long total;
    private Long totalResults;
    private Long page;
    private Long totalPage;
    private T data;

    public GeneralResponse(WorkflowStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public GeneralResponse(WorkflowStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

