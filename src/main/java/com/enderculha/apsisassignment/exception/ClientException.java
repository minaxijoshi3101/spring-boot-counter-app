package com.enderculha.apsisassignment.exception;

import com.enderculha.apsisassignment.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ClientException extends RuntimeException {

  private HttpStatus httpStatus;

  private String message;

  private String error;

  public ErrorDto getBody() {
    return new ErrorDto(httpStatus, message, error);
  }


}
