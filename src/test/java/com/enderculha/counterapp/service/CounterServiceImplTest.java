package com.enderculha.counterapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enderculha.counterapp.dto.CounterDto;
import com.enderculha.counterapp.repository.CounterRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CounterServiceImplTest {

  private final CounterRepository repository = mock(CounterRepository.class);

  private final CounterService service = new CounterServiceImpl(repository);

  private static final String COUNTER_ID = "test";
  private static final Long COUNTER_VALUE_0 = 0L;

  @Test
  public void getCounterShouldCallRepositoryFindByIdMethod() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, 0L);
    when(repository.findByCounterId(eq(COUNTER_ID))).thenReturn(Optional.ofNullable(counterDto));

    Optional<CounterDto> actual = service.getCounter(COUNTER_ID);

    assertTrue(actual.isPresent());
    assertEquals(counterDto, counterDto);
    verify(repository, times(1)).findByCounterId(eq(COUNTER_ID));

  }

  @Test
  public void getCounterListShouldCallRepositoryFindAllMethod() {

    service.getCounterList();

    verify(repository, times(1)).findAll();

  }

  @Test
  public void createCounterShouldCallRepositorySaveMethod() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(repository.save(eq(counterDto))).thenReturn(counterDto);

    CounterDto actual = service.createCounter(counterDto);

    assertEquals(counterDto, actual);
    verify(repository, times(1)).save(eq(counterDto));

  }

  @Test
  public void incrementCounterShouldCallRepositoryIncrementCounterMethod() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(repository.incrementCounter(eq(COUNTER_ID))).thenReturn(counterDto);

    CounterDto actual = service.incrementCounter(COUNTER_ID);

    assertEquals(counterDto, actual);
    verify(repository, times(1)).incrementCounter(eq(COUNTER_ID));

  }

}
