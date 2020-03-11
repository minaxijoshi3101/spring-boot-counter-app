package com.enderculha.counterapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enderculha.counterapp.dto.CounterDto;
import com.enderculha.counterapp.exception.ClientException;
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

  private static final String COUNTER_ID = "test";
  private static final Long COUNTER_VALUE_0 = 0L;
  private static final Long COUNTER_VALUE_1 = 1L;

  @Test
  public void saveShouldThrowClientExceptionWhenThereExistEntryWithTheSameKey() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(map.containsKey(eq(COUNTER_ID))).thenReturn(true);

    Exception exception = assertThrows(ClientException.class, () ->
        counterHashMapRepository.save(counterDto));

    String expectedMessage = "Counter conflict";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    verify(map, times(1)).containsKey(eq(COUNTER_ID));

  }

  @Test
  public void saveShouldPutToHashMap() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(map.containsKey(eq(COUNTER_ID))).thenReturn(false);
    when(map.get(eq(COUNTER_ID))).thenReturn(counterDto);

    CounterDto actual = counterHashMapRepository.save(counterDto);

    assertEquals(counterDto, actual);
    verify(map, times(1)).containsKey(eq(COUNTER_ID));
    verify(map, times(1)).get(eq(COUNTER_ID));

  }

  @Test
  public void findByIdShouldGetValueFromMap() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(map.containsKey(eq(COUNTER_ID))).thenReturn(true);
    when(map.get(eq(COUNTER_ID))).thenReturn(counterDto);

    Optional<CounterDto> actual = counterHashMapRepository.findByCounterId(COUNTER_ID);

    assertEquals(Optional.of(counterDto), actual);
    verify(map, times(1)).containsKey(eq(COUNTER_ID));

  }

  @Test
  public void findAll() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    String counterId2 = "test2";
    CounterDto counterDto2 = new CounterDto(counterId2, COUNTER_VALUE_0);
    Map hashMap = new HashMap<>();
    hashMap.put(COUNTER_ID, counterDto);
    hashMap.put(counterId2, counterDto2);
    when(map.entrySet()).thenReturn(hashMap.entrySet());

    List<CounterDto> actual = counterHashMapRepository.findAll();

    assertEquals(Arrays.asList(counterDto2, counterDto), actual);
    verify(map, times(1)).entrySet();

  }

  @Test
  public void incrementCounterShouldThrowExceptionWhenCounterIsNotFoundInMap() {

    when(map.containsKey(eq(COUNTER_ID))).thenReturn(false);

    Exception exception = assertThrows(ClientException.class, () ->
        counterHashMapRepository.incrementCounter(COUNTER_ID)
    );

    String expectedMessage = "Counter needs to be created first";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    verify(map, times(1)).containsKey(eq(COUNTER_ID));

  }

  @Test
  public void incrementCounterShouldIncrementValueInMap() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    CounterDto incrementedCounterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_1);
    when(map.containsKey(eq(COUNTER_ID))).thenReturn(true);
    when(map.get(eq(COUNTER_ID))).thenReturn(counterDto);

    CounterDto actual = counterHashMapRepository.incrementCounter(COUNTER_ID);
    assertEquals(incrementedCounterDto, actual);
    verify(map, times(1)).put(eq(COUNTER_ID), eq(incrementedCounterDto));

  }


}
