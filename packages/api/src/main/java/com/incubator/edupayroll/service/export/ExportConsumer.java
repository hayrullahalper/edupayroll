package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.configuration.queue.QueueConfig;
import com.incubator.edupayroll.entity.export.ExportStatus;
import com.incubator.edupayroll.service.document.DocumentService;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExportConsumer {
  private final ExportService exportService;
  private final DocumentService documentService;
  private final ExportFileGenerator exportFileGenerator;

  @Autowired
  public ExportConsumer(
      ExportService exportService,
      DocumentService documentService,
      ExportFileGenerator exportFileGenerator) {
    this.exportService = exportService;
    this.documentService = documentService;
    this.exportFileGenerator = exportFileGenerator;
  }

  @RabbitListener(queues = QueueConfig.QUEUE_NAME)
  public void handleExportTask(String exportId) {
    var export = exportService.getById(UUID.fromString(exportId));
    var document = documentService.getById(export.getDocument().getId());

    exportService.updateStatus(export, ExportStatus.IN_PROGRESS);

    try {
      List<ExportContentBuilder> builders = List.of(new FormalExportContentBuilder());
      var path = exportFileGenerator.generate(document, builders);

      exportService.updatePath(export, path);
      exportService.updateStatus(export, ExportStatus.COMPLETED);
    } catch (Exception e) {
      exportService.updateStatus(export, ExportStatus.FAILED);
    }
  }
}
