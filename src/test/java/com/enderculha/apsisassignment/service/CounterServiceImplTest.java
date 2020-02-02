package com.enderculha.apsisassignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.repository.CounterRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CounterServiceImplTest {

  private final CounterRepository repository = mock(CounterRepository.class);

  private final CounterService service = new CounterServiceImpl(repository);

  @Test
  public void getCounterShouldCallRepositoryFindByIdMethod() {

    String counterId = "test";
    CounterDto counterDto = new CounterDto(counterId, 0L);
    when(repository.findByCounterId(eq(counterId))).thenReturn(Optional.ofNullable(counterDto));

    Optional<CounterDto> actual = service.getCounter(counterId);

    assertTrue(actual.isPresent());
    assertEquals(counterDto, counterDto);
    verify(repository, times(1)).findByCounterId(eq(counterId));

  }

  @Test
  public void getCounterListShouldCallRepositoryFindAllMethod() {

    service.getCounterList();

    verify(repository, times(1)).findAll();

  }

  @Test
  public void createCounterShouldCallRepositorySaveMethod() {

    CounterDto counterDto = new CounterDto("test", 0L);
    when(repository.save(eq(counterDto))).thenReturn(counterDto);

    CounterDto actual = service.createCounter(counterDto);

    assertEquals(counterDto, actual);
    verify(repository, times(1)).save(eq(counterDto));

  }

  @Test
  public void incrementCounterShouldCallRepositoryIncrementCounterMethod() {

    String counterId = "test";
    CounterDto counterDto = new CounterDto("test", 0L);
    when(repository.incrementCounter(eq(counterId))).thenReturn(counterDto);

    CounterDto actual = service.incrementCounter(counterId);

    assertEquals(counterDto, actual);
    verify(repository, times(1)).incrementCounter(eq(counterId));

  }

}
