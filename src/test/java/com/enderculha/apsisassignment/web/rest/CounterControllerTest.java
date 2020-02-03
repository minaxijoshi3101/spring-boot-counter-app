package com.enderculha.apsisassignment.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.service.CounterService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CounterControllerTest {

  private final CounterService service = mock(CounterService.class);

  private final CounterController counterController = new CounterController(service);

  private static final String COUNTER_ID = "test";
  private static final Long COUNTER_VALUE_0 = 0L;
  private static final Long COUNTER_VALUE_1 = 1L;

  @Test
  public void getCounterShouldReturn200OK() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);

    when(service.getCounter(COUNTER_ID)).thenReturn(Optional.of(counterDto));

    ResponseEntity<CounterDto> actual = counterController.getCounter(COUNTER_ID);

    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertEquals(counterDto, actual.getBody());
    verify(service, times(1)).getCounter(COUNTER_ID);

  }

  @Test
  public void getCounterShouldReturn404NotFound() {

    when(service.getCounter(COUNTER_ID)).thenReturn(Optional.ofNullable(null));

    ResponseEntity<CounterDto> actual = counterController.getCounter(COUNTER_ID);

    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    verify(service, times(1)).getCounter(COUNTER_ID);

  }

  @Test
  public void listCountersShouldReturn200OK() {

    when(service.getCounterList()).thenReturn(Collections.emptyList());

    ResponseEntity<List<CounterDto>> actual = counterController.listCounters();

    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertEquals(Collections.emptyList(), actual.getBody());
    verify(service, times(1)).getCounterList();

  }

  @Test
  public void createCounterShouldReturn201Created() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_0);
    when(service.createCounter(eq(counterDto))).thenReturn(counterDto);

    ResponseEntity<CounterDto> actual = counterController.createCounter(counterDto);

    assertEquals(HttpStatus.CREATED, actual.getStatusCode());
    assertEquals(counterDto, actual.getBody());
    verify(service, times(1)).createCounter(eq(counterDto));

  }

  @Test
  public void incrementCounterShouldReturn200OK() {

    CounterDto counterDto = new CounterDto(COUNTER_ID, COUNTER_VALUE_1);
    when(service.incrementCounter(eq(COUNTER_ID))).thenReturn(counterDto);

    ResponseEntity<CounterDto> actual = counterController.incrementCounter(COUNTER_ID);

    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertEquals(counterDto, actual.getBody());
    verify(service, times(1)).incrementCounter(eq(COUNTER_ID));

  }

}
