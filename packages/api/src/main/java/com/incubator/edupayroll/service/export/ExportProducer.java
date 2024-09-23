package com.incubator.edupayroll.service.export;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportProducer {

  private final Queue queue;
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public ExportProducer(Queue queue, RabbitTemplate rabbitTemplate) {
    this.queue = queue;
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendExportCreationTask(String exportId) {
    rabbitTemplate.convertAndSend(queue.getName(), exportId);
  }
}
