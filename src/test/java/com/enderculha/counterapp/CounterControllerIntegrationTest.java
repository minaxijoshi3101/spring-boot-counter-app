package com.enderculha.counterapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.enderculha.counterapp.dto.CounterDto;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = CounterApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class CounterControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private static final String HOST_URL = "http://localhost:";
  private static final String ENDPOINT_LIST = "/counters";
  private static final String ENDPOINT_COUNTER_GET = ENDPOINT_LIST + "/counter";
  private static final String ENDPOINT_INCREMENT = ENDPOINT_COUNTER_GET + "/increment";


  @Test
  public void testAddCounterAndIncrementWithMultiThreadsAndCheckFinalValue() {
    CounterDto counterDto = new CounterDto("counter", 0L);
    ResponseEntity<CounterDto> responseEntity = this.restTemplate
        .postForEntity(HOST_URL + port + ENDPOINT_LIST, counterDto, CounterDto.class);
    assertEquals(201, responseEntity.getStatusCodeValue());

    ExecutorService executorService =
        Executors.newFixedThreadPool(10);

    for (int j = 0; j < 100; j++) {
      executorService.execute(() -> {
        ResponseEntity<CounterDto> responseEntity2 = this.restTemplate
            .exchange(HOST_URL + port + ENDPOINT_INCREMENT, HttpMethod.PUT, null,
                CounterDto.class);

        assertEquals(200, responseEntity2.getStatusCodeValue());
        log.debug("Counter Value:{}", responseEntity2.getBody().getValue());
      });
    }

    awaitTerminationAfterShutdown(executorService);

    CounterDto responseEntity3 = this.restTemplate
        .getForObject(HOST_URL + port + ENDPOINT_COUNTER_GET, CounterDto.class);

    assertEquals(100, responseEntity3.getValue());

  }

  public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
    threadPool.shutdown();
    try {
      if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
        threadPool.shutdownNow();
      }
    } catch (InterruptedException ex) {
      threadPool.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}
