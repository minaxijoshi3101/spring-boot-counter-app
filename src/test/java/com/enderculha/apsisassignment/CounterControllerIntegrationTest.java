package com.enderculha.apsisassignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.enderculha.apsisassignment.dto.CounterDto;
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

@SpringBootTest(classes = ApsisAssignmentApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class CounterControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  public void testAddCounterAndIncrementWithMultiThreads() {
    CounterDto counterDto = new CounterDto("counter", 0L);
    ResponseEntity<CounterDto> responseEntity = this.restTemplate
        .postForEntity("http://localhost:" + port + "/counters", counterDto, CounterDto.class);
    assertEquals(201, responseEntity.getStatusCodeValue());

    ExecutorService executorService =
        Executors.newFixedThreadPool(4);

    for (int j = 0; j < 20; j++) {
      executorService.execute(() -> {
        ResponseEntity<CounterDto> responseEntity2 = this.restTemplate
            .exchange("http://localhost:" + port + "/counters/counter/increment", HttpMethod.PUT, null,
                CounterDto.class);

        assertEquals(200, responseEntity2.getStatusCodeValue());
        log.debug("Counter Value:{}", responseEntity2.getBody().getValue());
      });
    }

  }

  @Test
  public void getCounter() throws InterruptedException {

    TimeUnit.SECONDS.sleep(5);
    CounterDto responseEntity3 = this.restTemplate
        .getForObject("http://localhost:" + port + "/counters/counter", CounterDto.class);

    assertEquals(20, responseEntity3.getValue());
  }
}
