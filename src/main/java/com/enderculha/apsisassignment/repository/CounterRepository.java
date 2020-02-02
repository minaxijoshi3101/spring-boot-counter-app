package com.enderculha.apsisassignment.repository;

import com.enderculha.apsisassignment.dto.CounterDto;
import java.util.List;
import java.util.Optional;

public interface CounterRepository {

  CounterDto save(CounterDto counterDto);

  Optional<CounterDto> findByCounterId(String counterId);

  List<CounterDto> findAll();

  CounterDto incrementCounter();

}
