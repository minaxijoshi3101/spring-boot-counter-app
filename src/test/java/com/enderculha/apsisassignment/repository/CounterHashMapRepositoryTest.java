package com.enderculha.apsisassignment.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.exception.ClientException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

class CounterHashMapRepositoryTest {

  private final ConcurrentHashMap map = mock(ConcurrentHashMap.class);
  private final CounterHashMapRepository counterHashMapRepository = new CounterHashMapRepository(map);

  @Test
  public void saveShouldThrowClientExceptionWhenThereExistEntryWithTheSameKey() {

    String counterId = "test";
    CounterDto counterDto = new CounterDto(counterId, 0L);
    when(map.containsKey(eq(counterId))).thenReturn(true);

    Exception exception = assertThrows(ClientException.class, () -> {
      counterHashMapRepository.save(counterDto);
    });

    String expectedMessage = "Counter conflict";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    verify(map, times(1)).containsKey(eq(counterId));

  }

  @Test
  public void saveShouldPutToHashMap() {

    String counterId = "test";
    CounterDto counterDto = new CounterDto(counterId, 0L);
    when(map.containsKey(eq(counterId))).thenReturn(false);
    when(map.get(eq(counterId))).thenReturn(counterDto);

    CounterDto actual = counterHashMapRepository.save(counterDto);

    assertEquals(counterDto, actual);
    verify(map, times(1)).containsKey(eq(counterId));
    verify(map, times(1)).get(eq(counterId));

  }

  @Test
  public void findByIdShouldGetValueFromMap() {

    String counterId = "test";
    CounterDto counterDto = new CounterDto(counterId, 0L);
    when(map.containsKey(eq(counterId))).thenReturn(true);
    when(map.get(eq(counterId))).thenReturn(counterDto);

    Optional<CounterDto> actual = counterHashMapRepository.findByCounterId(counterId);

    assertEquals(Optional.of(counterDto), actual);
    verify(map, times(1)).containsKey(eq(counterId));

  }

  @Test
  public void findAll() {

    String counterId1 = "test";
    CounterDto counterDto = new CounterDto(counterId1, 0L);
    String counterId2 = "test2";
    CounterDto counterDto2 = new CounterDto(counterId2, 0L);
    Map hashMap = new HashMap<>();
    hashMap.put(counterId1, counterDto);
    hashMap.put(counterId2, counterDto2);
    when(map.entrySet()).thenReturn(hashMap.entrySet());

    List<CounterDto> actual = counterHashMapRepository.findAll();

    assertEquals(Arrays.asList(counterDto2, counterDto), actual);
    verify(map, times(1)).entrySet();

  }

  @Test
  public void incrementCounterShouldThrowExceptionWhenCounterIsNotFoundInMap() {

    String counterId = "test";
    when(map.containsKey(eq(counterId))).thenReturn(false);

    Exception exception = assertThrows(ClientException.class, () -> {
      counterHashMapRepository.incrementCounter(counterId);
    });

    String expectedMessage = "Counter needs to be created first";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    verify(map, times(1)).containsKey(eq(counterId));

  }

  @Test
  public void incrementCounterShouldIncrementValueInMap() {
    String counterId = "test";
    CounterDto counterDto = new CounterDto(counterId, 0L);
    CounterDto incrementedCounterDto = new CounterDto(counterId, 1L);
    when(map.containsKey(eq(counterId))).thenReturn(true);
    when(map.get(eq(counterId))).thenReturn(counterDto);

    CounterDto actual = counterHashMapRepository.incrementCounter(counterId);
    assertEquals(incrementedCounterDto, actual);
    verify(map, times(1)).put(eq(counterId), eq(incrementedCounterDto));

  }


}
