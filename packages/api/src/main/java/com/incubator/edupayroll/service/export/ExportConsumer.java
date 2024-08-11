package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.configuration.queue.QueueConfig;
import com.incubator.edupayroll.entity.export.ExportStatus;
import com.incubator.edupayroll.service.document.DocumentService;
import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExportConsumer {
  private final ExportService exportService;
  private final DocumentService documentService;

  @Autowired
  public ExportConsumer(ExportService exportService, DocumentService documentService) {
    this.exportService = exportService;
    this.documentService = documentService;
  }

  @RabbitListener(queues = QueueConfig.QUEUE_NAME)
  public void handleExportTask(UUID exportId) {
    var export = exportService.getById(exportId);
    var document = documentService.getById(export.getDocument().getId());

    exportService.updateStatus(export, ExportStatus.IN_PROGRESS);

    try {

      System.out.println("document name: " + document.getName());
      System.out.println("document user: " + document.getUser().getEmail());
      System.out.println("document records: " + document.getRecords().size());

      for (var record : document.getRecords()) {
        System.out.println(" - record id: " + record.getId());
        System.out.println(" - record type: " + record.getType());
        System.out.println(" - record teacher: " + record.getTeacher().getId());
      }

      exportService.updateStatus(export, ExportStatus.COMPLETED);
    } catch (Exception e) {
      exportService.updateStatus(export, ExportStatus.FAILED);
    }
  }
}
