package com.enderculha.apsisassignment.repository;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.exception.ClientException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@Data
@Slf4j
public class CounterHashMapRepository implements CounterRepository {

  private Map<String, CounterDto> counterMap;

  @Autowired
  public CounterHashMapRepository(Map<String, CounterDto> counterMap) {
    this.counterMap = counterMap;
  }

  @Override
  public CounterDto save(CounterDto counterDto) {

    if (counterMap.containsKey(counterDto.getId())) {
      log.warn("Counter conflict, existing counter is requested to be overriden, with Counter Id:{}",
          counterDto.getId());
      throw new ClientException(HttpStatus.FORBIDDEN,
          "Counter conflict",
          "Counter:" + counterDto.getId() + " can not be created again"
      );
    }

    counterMap.put(counterDto.getId(), counterDto);
    return counterMap.get(counterDto.getId());
  }

  @Override
  public Optional<CounterDto> findByCounterId(String counterId) {

    if (counterMap.containsKey(counterId)) {
      return Optional.of(counterMap.get(counterId));
    }
    log.warn("Non existing counter with Id:{} is requested to be fetched", counterId);
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
      log.warn("Non existing counter with Id:{} is requested to be incremented",
          counterId);
      throw new ClientException(HttpStatus.NOT_FOUND, "Counter needs to be created first",
          "Counter with Id:" + counterId + " does not exist");
    }

    Long currentValue = counterMap.get(counterId).getValue();
    if (currentValue == Long.MAX_VALUE) {
      log.warn("Counter with Id:{} is overflowed", counterId);
    }

    CounterDto newCounter = new CounterDto(counterId, currentValue + 1);
    counterMap.put(counterId, newCounter);
    return newCounter;
  }
}
