package com.incubator.edupayroll.configuration.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
  public static final String QUEUE_NAME = "export-queue";

  @Bean
  public Queue getQueue() {
    return new Queue(QUEUE_NAME, false);
  }
}
