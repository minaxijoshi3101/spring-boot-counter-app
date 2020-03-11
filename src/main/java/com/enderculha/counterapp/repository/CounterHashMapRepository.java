package com.enderculha.counterapp.repository;

import com.enderculha.counterapp.dto.CounterDto;
import com.enderculha.counterapp.exception.ClientException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Data
@Slf4j
public class CounterHashMapRepository implements CounterRepository {

  //TODO: Some parts here should be moved to Service Layer
  //TODO: Use Model Object instead of CounterDto in Repository Layer

  private Map<String, CounterDto> counterMap;

  public CounterHashMapRepository(Map<String, CounterDto> counterMap) {
    this.counterMap = counterMap;
  }

  @Override
  public synchronized CounterDto save(CounterDto counterDto) {

    if (counterMap.containsKey(counterDto.getId())) {
      log.warn("Counter conflict, existing counter is requested to be overriden, with Counter Id:{}",
          counterDto.getId());
      throw new ClientException(HttpStatus.FORBIDDEN,
          "Counter conflict",
          "Counter:" + counterDto.getId() + " can not be created again"
      ); //TODO: Remove ClientException from Repository Layer
    }

    counterMap.put(counterDto.getId(), counterDto);
    return counterMap.get(counterDto.getId());
  }

  @Override
  public synchronized Optional<CounterDto> findByCounterId(String counterId) {

    if (counterMap.containsKey(counterId)) {
      return Optional.of(counterMap.get(counterId));
    }
    log.warn("Non existing counter with Id:{} is requested to be fetched", counterId);
    return Optional.empty();

  }

  @Override
  public List<CounterDto> findAll() {
    return new ArrayList<>(counterMap
        .values()); //TODO: Change method return from List to Collection so as to get rid of extra conversion
  }

  @Override
  //TODO: To increase performance, remove synchronized from method and use concurrent hashmap atomic update https://dzone.com/articles/java-8-concurrenthashmap-atomic-updates
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
