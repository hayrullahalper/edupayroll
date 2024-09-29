package com.incubator.edupayroll.controller.document;

import com.incubator.edupayroll.common.response.PageResponse;
import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.dto.document.*;
import com.incubator.edupayroll.dto.export.Export;
import com.incubator.edupayroll.mapper.document.DocumentMapper;
import com.incubator.edupayroll.mapper.export.ExportMapper;
import com.incubator.edupayroll.service.document.DocumentService;
import com.incubator.edupayroll.service.export.ExportProducer;
import com.incubator.edupayroll.service.export.ExportService;
import com.incubator.edupayroll.service.record.RecordService;
import com.incubator.edupayroll.service.user.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class DocumentController {
  private final UserService userService;
  private final ExportService exportService;
  private final RecordService recordService;
  private final ExportProducer exportProducer;
  private final DocumentService documentService;

  @Autowired
  public DocumentController(
      UserService userService,
      ExportService exportService,
      RecordService recordService,
      ExportProducer exportProducer,
      DocumentService documentService) {
    this.userService = userService;
    this.exportService = exportService;
    this.recordService = recordService;
    this.exportProducer = exportProducer;
    this.documentService = documentService;
  }

  @GetMapping("")
  public ResponseEntity<PageResponse<Document, DocumentErrorCode>> getDocuments(
      @RequestParam("limit") int limit,
      @RequestParam("offset") int offset,
      @RequestParam(value = "name", required = false) Optional<String> name) {
    var user = userService.getAuthenticatedUser();

    var count = documentService.count(user, name);
    var documents =
        documentService.getAll(user, limit, offset, name).stream()
            .map(DocumentMapper::toDTO)
            .toList();

    return ResponseEntity.ok()
        .body(PageResponse.data(documents).meta(limit, offset, count).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<Document, DocumentErrorCode>> getDocument(
      @PathVariable String id) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    return ResponseEntity.ok().body(Response.data(DocumentMapper.toDTO(document)).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<Document, DocumentErrorCode>> updateDocument(
      @PathVariable String id, @Valid @RequestBody DocumentUpdateInput input) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    var updatedDocument = documentService.update(document, input.getName(), input.getTime());

    return ResponseEntity.ok().body(Response.data(DocumentMapper.toDTO(updatedDocument)).build());
  }

  @PostMapping("")
  public ResponseEntity<Response<Document, DocumentErrorCode>> createDocument(
      @Valid @RequestBody DocumentCreateInput input) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.create(user, input.getName(), input.getTime());

    return ResponseEntity.ok().body(Response.data(DocumentMapper.toDTO(document)).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<DocumentDeletePayload, DocumentErrorCode>> deleteDocument(
      @PathVariable String id) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    documentService.remove(document);

    return ResponseEntity.ok().body(Response.data(new DocumentDeletePayload(true)).build());
  }

  @PutMapping("/{id}/clear/exports")
  public ResponseEntity<Response<DocumentUpdatePayload, DocumentErrorCode>> clearExports(
      @PathVariable String id) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    exportService.removeAllByDocument(document);

    return ResponseEntity.ok().body(Response.data(new DocumentUpdatePayload(true)).build());
  }

  @PutMapping("/{id}/clear/records")
  public ResponseEntity<Response<DocumentUpdatePayload, DocumentErrorCode>> clearRecords(
      @PathVariable String id) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    recordService.removeAllByDocument(document);

    return ResponseEntity.ok().body(Response.data(new DocumentUpdatePayload(true)).build());
  }

  @PostMapping("/{id}/export")
  public ResponseEntity<Response<Export, DocumentErrorCode>> exportDocument(
      @PathVariable String id, @RequestBody DocumentExportInput input) {
    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    var export = exportService.create(document, input.getName());
    exportProducer.sendExportCreationTask(export.getId().toString());

    return ResponseEntity.ok().body(Response.data(ExportMapper.toDTO(export)).build());
  }
}
