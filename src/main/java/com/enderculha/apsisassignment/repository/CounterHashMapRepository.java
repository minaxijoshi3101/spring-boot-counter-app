package com.enderculha.apsisassignment.repository;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.exception.ClientException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class CounterHashMapRepository implements CounterRepository {

  private volatile Map<String, CounterDto> counterMap;

  @Autowired
  public CounterHashMapRepository(Map<String, CounterDto> counterMap) {
    this.counterMap = counterMap;
  }

  @Override
  public CounterDto save(CounterDto counterDto) {

    if (counterMap.containsKey(counterDto.getName())) {
      throw new ClientException(HttpStatus.FORBIDDEN,
          "Counter conflict",
          "Counter:" + counterDto.getName() + " can not be created again"
      );
    }

    counterMap.put(counterDto.getName(), counterDto);
    return counterMap.get(counterDto.getName());
  }

  @Override
  public Optional<CounterDto> findByCounterId(String counterId) {
    if (counterMap.containsKey(counterId)) {
      return Optional.of(counterMap.get(counterId));
    }
    return Optional.empty();
  }

  @Override
  public List<CounterDto> findAll() {
    return counterMap
        .entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }

  @Override
  public synchronized CounterDto incrementCounter(String counterId) {
    if (!counterMap.containsKey(counterId)) {
      throw new ClientException(HttpStatus.NOT_FOUND, "Counter needs to be created first",
          "Counter with Id:" + counterId + " does not exist");
    }
    CounterDto newCounter = new CounterDto(counterId, counterMap.get(counterId).getValue() + 1);
    counterMap.put(counterId, newCounter);
    return newCounter;
  }
}
