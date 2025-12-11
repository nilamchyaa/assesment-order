package org.example.exception;

import org.example.constants.WorkflowStatus;
import org.example.dto.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> uniqueErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        LinkedHashMap::new,
                        (map, error) -> map.putIfAbsent(error.getField(), error.getDefaultMessage()),
                        Map::putAll
                );
        List<String> errors = new ArrayList<>(uniqueErrors.values());


        return ResponseEntity.badRequest()
                .body(new GeneralResponse<>(WorkflowStatus.FAILED, errors.toString()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<GeneralResponse<Object>> handleItemNotFound(ItemNotFoundException ex) {
        return ResponseEntity.badRequest()
                .body(new GeneralResponse<>(WorkflowStatus.FAILED, ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GeneralResponse<Object>> handleRuntimeException(RuntimeException ex) {
        GeneralResponse<Object> response = new GeneralResponse<>(
                WorkflowStatus.FAILED,
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }

}

