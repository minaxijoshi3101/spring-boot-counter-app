package com.enderculha.apsisassignment.web.rest;

import com.enderculha.apsisassignment.dto.CounterDto;
import com.enderculha.apsisassignment.service.CounterService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CounterController {

  private final CounterService service;

  @Autowired
  public CounterController(CounterService service) {
    this.service = service;
  }

  @GetMapping("/counters/{counterId}")
  public ResponseEntity<CounterDto> getCounter(@PathVariable("counterId") String counterId) {
    Optional<CounterDto> counterOptional = service.getCounter(counterId);
    if (counterOptional.isPresent()) {
      return ResponseEntity.ok(counterOptional.get());
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/counters")
  public ResponseEntity<List<CounterDto>> listCounters() {
    return ResponseEntity.ok(service.getCounterList());
  }

  @PostMapping("/counters")
  public ResponseEntity<CounterDto> createCounter(@RequestBody @Valid CounterDto counterDto) {
    CounterDto createdCounter = service.createCounter(counterDto);
    return new ResponseEntity<>(createdCounter, HttpStatus.CREATED);
  }

  @PutMapping("/counters/{counterId}/increment")
  public ResponseEntity<CounterDto> incrementCounter(@PathVariable("counterId") String counterId) {
    CounterDto createdCounter = service.incrementCounter(counterId);
    return new ResponseEntity<>(createdCounter, HttpStatus.OK);
  }

}
