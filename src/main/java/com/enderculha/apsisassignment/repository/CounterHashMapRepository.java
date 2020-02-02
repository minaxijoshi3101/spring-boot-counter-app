package com.enderculha.apsisassignment.repository;

import com.enderculha.apsisassignment.dto.CounterDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CounterHashMapRepository implements CounterRepository {

  private static Map<String, CounterDto> counterMap = new ConcurrentHashMap<>();

  @Override
  public CounterDto save(CounterDto counterDto) {
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
  public CounterDto incrementCounter() {
    return null;
  }
}
