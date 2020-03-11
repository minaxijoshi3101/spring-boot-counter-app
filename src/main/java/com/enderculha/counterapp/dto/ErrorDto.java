package com.enderculha.counterapp.dto;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorDto {

  private HttpStatus status;

  private String message;

  private List<String> errors;

  public ErrorDto(HttpStatus status, String message, String error) {
    this.status = status;
    this.message = message;
    errors = Arrays.asList(error);
  }

}
