package com.booking.exception.handler;

import com.booking.config.ErrorConfig;
import com.booking.error.ErrorResponse;
import com.booking.error.FieldErrorDetail;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private ErrorConfig errorConfig;

    // Bean Validation: @Valid on @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new FieldErrorDetail(
                        fe.getField(),
                        fe.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse resp = errorConfig.getError("validation", "INVALID_ARGUMENT");

        ErrorResponse body = new ErrorResponse(
                resp.code(),
                resp.message(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(cv -> new FieldErrorDetail(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse resp = errorConfig.getError("Constraint Violation", "MISSING_FIELD");

        ErrorResponse body = new ErrorResponse(
                resp.code(),
                resp.message(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse resp = errorConfig.getError("internal", "INTERNAL_ERROR");
        ErrorResponse body = new ErrorResponse(
                resp.code(),
                resp.message(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
