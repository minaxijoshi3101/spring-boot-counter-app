package com.enderculha.apsisassignment.exception;

import com.enderculha.apsisassignment.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ClientException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final String message;

  private final String error;

  public ErrorDto getBody() {
    return new ErrorDto(httpStatus, message, error);
  }


}
