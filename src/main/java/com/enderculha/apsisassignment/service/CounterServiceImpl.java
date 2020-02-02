package com.enderculha.apsisassignment.service;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.exception.ClientException;
import com.enderculha.apsisassignment.repository.CounterRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    if (counterRepository.findByCounterId(counterDto.getName()).isPresent()) {
      throw new ClientException(HttpStatus.FORBIDDEN,
          "Counter conflict",
          "Counter:" + counterDto.getName() + " can not be created again"
      );
    }
    return counterRepository.save(counterDto);
  }

  @Override
  public CounterDto incrementCounter(String counterId) {

    var counter = counterRepository.findByCounterId(counterId)
        .orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "Counter not found",
            "Counter with Id:" + counterId + " needs to be created first"));

    CounterDto counterDto = new CounterDto(counterId, counter.getValue() + 1);
    counterRepository.save(counterDto);

    return counterRepository.findByCounterId(counterId).get();

  }
}
