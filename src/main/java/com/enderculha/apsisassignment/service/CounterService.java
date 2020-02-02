package com.enderculha.apsisassignment.service;

import com.enderculha.apsisassignment.dto.CounterDto;
import java.util.List;
import java.util.Optional;

public interface CounterService {

  Optional<CounterDto> getCounter(String counterId);

  List<CounterDto> getCounterList();

  CounterDto createCounter(CounterDto counterDto);

  CounterDto incrementCounter(String counterId);

}
