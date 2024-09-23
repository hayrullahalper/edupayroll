package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.service.storage.StorageService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportFileGenerator {
  private final StorageService storageService;

  @Autowired
  public ExportFileGenerator(StorageService storageService) {
    this.storageService = storageService;
  }

  public String generate(DocumentEntity document, List<ExportContentBuilder> builders) {
    var file = generateTempFile(document);

    builders.forEach(builder -> builder.init(document));
    var files = builders.stream().map(ExportContentBuilder::build).toList();

    writeIntoZipFile(file, files);
    var path = uploadFile(file);

    file.delete();
    files.forEach(File::delete);

    return path;
  }

  private void writeIntoZipFile(File zipFile, List<File> files) {
    try (var zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
      for (var f : files) {
        var entry = new ZipEntry(f.getName());
        zos.putNextEntry(entry);

        try (var fis = new FileInputStream(f)) {
          byte[] buffer = new byte[1024];
          int length;
          while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
          }
        }

        zos.closeEntry();
      }
    } catch (IOException e) {
      throw new RuntimeException("Error while writing into zip file", e);
    }
  }

  private String uploadFile(File file) {
    try (var stream = new FileInputStream(file)) {
      var path = String.format("exports/%s", file.getName());

      storageService.uploadFile(path, stream);
      return path;
    } catch (IOException e) {
      throw new RuntimeException("Error while uploading file", e);
    }
  }

  private File generateTempFile(DocumentEntity document) {
    try {
      var name =
          String.format(
              "kbs-belgeler-%s-%s",
              document.getId().toString().substring(0, 5),
              String.format("%td-%<tm-%<tY", document.getCreatedAt()));
      return File.createTempFile(name, ".zip");
    } catch (IOException e) {
      throw new RuntimeException("Error while creating temporary file", e);
    }
  }
}
