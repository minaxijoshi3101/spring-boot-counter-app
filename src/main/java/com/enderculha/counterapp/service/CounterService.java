package com.enderculha.counterapp.service;

import com.enderculha.counterapp.dto.CounterDto;
import java.util.List;
import java.util.Optional;

public interface CounterService {

  Optional<CounterDto> getCounter(String counterId);

  List<CounterDto> getCounterList();

  CounterDto createCounter(CounterDto counterDto);

  CounterDto incrementCounter(String counterId);

}
