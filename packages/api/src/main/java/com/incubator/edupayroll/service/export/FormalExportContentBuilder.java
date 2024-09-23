package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormalExportContentBuilder implements ExportContentBuilder {
  private Workbook workbook;
  private DocumentEntity document;

  @Override
  public void init(DocumentEntity document) {
    this.document = document;
    this.workbook = new XSSFWorkbook();
  }

  @Override
  public File build() {
    var sheet = workbook.createSheet("KBS");
    var numberOfDays = document.getTime().lengthOfMonth();

    initializeHeaders(sheet, numberOfDays);

    int index = 1;
    for (var record : document.getRecords()) {
      populateRecord(sheet, index++, record, numberOfDays);
    }

    return saveAsFile();
  }

  private void initializeHeaders(Sheet sheet, int numberOfDays) {
    var header = sheet.createRow(0);
    header.createCell(0).setCellValue("TCKN");
    header.createCell(1).setCellValue("Veri Tip");

    for (int i = 0; i < numberOfDays; i++) {
      var cell = header.createCell(i + 2);
      cell.setCellValue("Gun" + (i + 1));
    }
  }

  private void populateRecord(Sheet sheet, int index, RecordEntity record, int numberOfDays) {
    var row = sheet.createRow(index);

    row.createCell(0).setCellValue(record.getTeacher().getIdNumber());
    row.createCell(1).setCellValue(record.getType().getCode());

    for (int i = 0; i < numberOfDays; i++) {
      var cell = row.createCell(i + 2);
      var hour = record.getHours().size() > i ? record.getHours().get(i) : 0;

      cell.setCellValue(hour);
    }
  }

  private File saveAsFile() {
    try {
      var name =
          String.format(
              "kbs-sistem-%s-%s",
              document.getId().toString().substring(0, 5),
              String.format("%td-%<tm-%<tY", document.getCreatedAt()));
      var file = File.createTempFile(name, ".xlsx");

      try (var out = new FileOutputStream(file)) {
        workbook.write(out);
      }

      return file;
    } catch (IOException e) {
      throw new RuntimeException("Error while writing Excel file", e);
    }
  }
}
