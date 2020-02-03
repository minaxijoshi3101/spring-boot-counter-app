package com.enderculha.apsisassignment.configuration;

import com.enderculha.apsisassignment.repository.CounterHashMapRepository;
import com.enderculha.apsisassignment.repository.CounterRepository;
import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

  @Bean
  public CounterRepository counterRepository() {
    return new CounterHashMapRepository(new HashMap<>());
  }

}
