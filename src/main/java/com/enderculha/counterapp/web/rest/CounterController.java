package com.enderculha.counterapp.web.rest;

import com.enderculha.counterapp.dto.CounterDto;
import com.enderculha.counterapp.service.CounterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
  @ApiOperation(value = "Get counter and its value")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Counter is retrieved successfully."),
      @ApiResponse(code = 400, message = "Invalid input."),
      @ApiResponse(code = 404, message = "Counter does not exist")
  })
  public ResponseEntity<CounterDto> getCounter(@PathVariable("counterId") String counterId) {

    log.info("Get Counter Request is triggered for Counter Id:{}", counterId);
    Optional<CounterDto> counterOptional = service.getCounter(counterId);
    if (counterOptional.isPresent()) {
      return ResponseEntity.ok(counterOptional.get());
    }

    log.info("No Counter found for Counter Id:{}", counterId);
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/counters")
  @ApiOperation(value = "Lists all counters and its values")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Counters are retrieved successfully."),
      @ApiResponse(code = 400, message = "Invalid input.")
  })
  public ResponseEntity<List<CounterDto>> listCounters() {

    log.info("List Counter Request is triggered");
    return ResponseEntity.ok(service.getCounterList());

  }

  @PostMapping("/counters")
  @ApiOperation(value = "Creates a new Counter")
  @ApiImplicitParam(name = "counterDto", value = "Counter object with Id and Initial Value", dataType = "CounterDto",
      required = true)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Counter is created successfully."),
      @ApiResponse(code = 400, message = "Invalid input."),
      @ApiResponse(code = 403, message = "Forbidden since counter already exists")
  })
  public ResponseEntity<CounterDto> createCounter(@RequestBody @Valid CounterDto counterDto) {

    log.info("Create Counter is triggered with values, {}", counterDto);
    CounterDto createdCounter = service.createCounter(counterDto);
    return new ResponseEntity<>(createdCounter, HttpStatus.CREATED);

  }

  @PutMapping("/counters/{counterId}/increment")
  @ApiOperation(value = "Increments the counter value by 1")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Counter is incremented successfully."),
      @ApiResponse(code = 400, message = "Invalid input."),
  })
  public ResponseEntity<CounterDto> incrementCounter(@PathVariable("counterId") String counterId) {

    log.info("Increment Counter is triggered for Counter Id:{}", counterId);
    CounterDto createdCounter = service.incrementCounter(counterId);
    return new ResponseEntity<>(createdCounter, HttpStatus.OK);

  }

}
