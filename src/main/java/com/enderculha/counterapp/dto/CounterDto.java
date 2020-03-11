package com.enderculha.counterapp.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CounterDto implements Serializable {

  @NotEmpty(message = "field cannot be null or empty")
  private String id;

  private Long value;

}
