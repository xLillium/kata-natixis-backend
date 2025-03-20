package com.xlillium.kata_natixis_backend;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.xlillium.kata_natixis_backend.exceptions.DuplicateIsbnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String FIELD = "field";
    private static final String DETAILS = "details";
    private static final String PATH = "path";
    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed. See 'details' for more informations.";
    private static final String MALFORMED_REQUEST_BODY_MESSAGE = "The request body is missing or malformed.";
    private static final String UNKNOWN_FIELD_MESSAGE = "Unrecognized field";
    public static final String INVALID_FORMAT_EXPECTED_A = "Invalid format. Expected a ";

    private ResponseEntity<Object> buildErrorResponse(WebRequest request, HttpStatus status, String message, List<Map<String, String>> details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, status.value());
        body.put(ERROR, status.getReasonPhrase());
        body.put(MESSAGE, message);
        body.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        if (details != null) {
            body.put(DETAILS, details);
        }
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<Map<String, String>> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of(FIELD, fieldError.getField(),
                        MESSAGE, Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.toList());

        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, VALIDATION_FAILED_MESSAGE, details);
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<Object> handleDuplicateIsbnException(DuplicateIsbnException ex, WebRequest request) {
        return buildErrorResponse(request, HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        Throwable rootCause = ex.getMostSpecificCause();
        List<Map<String, String>> details = null;

        if (rootCause instanceof UnrecognizedPropertyException unrecognizedEx) {
            String unknownField = unrecognizedEx.getPropertyName();
            details = List.of(Map.of(FIELD, unknownField, MESSAGE, UNKNOWN_FIELD_MESSAGE));

        } else if (rootCause instanceof InvalidFormatException invalidFormatEx) {
            String fieldPath = invalidFormatEx.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("."));

            String targetType = invalidFormatEx.getTargetType().getSimpleName();
            details = List.of(Map.of(FIELD, fieldPath, MESSAGE, INVALID_FORMAT_EXPECTED_A + targetType));
        }

        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, MALFORMED_REQUEST_BODY_MESSAGE, details);
    }
}
