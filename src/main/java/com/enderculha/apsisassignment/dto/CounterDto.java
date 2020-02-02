package com.enderculha.apsisassignment.dto;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CounterDto implements Serializable {

  @NotEmpty(message = "field cannot be null")
  private String name;

  @Min(value = 0L, message = "field can not be lower than 0")
  @Max(value = 0L, message = "field can not be greater than 0")
  private Long value;

}
