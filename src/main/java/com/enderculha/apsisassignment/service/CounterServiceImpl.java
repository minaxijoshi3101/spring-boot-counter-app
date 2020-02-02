package com.enderculha.apsisassignment.service;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.repository.CounterRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

  private final CounterRepository counterRepository;

  @Autowired
  public CounterServiceImpl(CounterRepository counterRepository) {
    this.counterRepository = counterRepository;
  }

  @Override
  public Optional<CounterDto> getCounter(String counterId) {
    return counterRepository.findByCounterId(counterId);
  }

  @Override
  public List<CounterDto> getCounterList() {
    return counterRepository.findAll();
  }

  @Override
  public CounterDto createCounter(CounterDto counterDto) {

    return counterRepository.save(counterDto);
  }

  @Override
  public CounterDto incrementCounter(String counterId) {

    return counterRepository.incrementCounter(counterId);

  }
}
