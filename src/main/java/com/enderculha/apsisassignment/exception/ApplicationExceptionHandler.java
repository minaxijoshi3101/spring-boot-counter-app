package com.enderculha.apsisassignment.exception;

import com.enderculha.apsisassignment.dto.ErrorDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ClientException.class})
  public ResponseEntity<Object> handleClientException(ClientException ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getBody(), new HttpHeaders(), ex.getHttpStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    log.error("Method Argument not Valid Exception is handled with errors:{}", errors);
    ErrorDto errorDto =
        new ErrorDto(HttpStatus.BAD_REQUEST, "Input Validation Error", errors);
    return handleExceptionInternal(
        ex, errorDto, headers, errorDto.getStatus(), request);
  }


  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    log.error("HttpMessageNotReadable Exception is handled with message:{}", ex.getMessage());
    ErrorDto errorDto =
        new ErrorDto(status, ex.getMessage(), ex.getMostSpecificCause().getMessage());

    return ResponseEntity.status(status)
        .body(errorDto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleDefaultException(Exception ex) {
    String uuid = UUID.randomUUID().toString();
    log.error("Uncaught exception with ID[{}], message{}", uuid, ex.getMessage());
    ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
        "Uncaught exception with ID[{" + uuid + "}]");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errorDto);
  }

}
