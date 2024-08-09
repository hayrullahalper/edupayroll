package com.incubator.edupayroll.controller.document;

import com.incubator.edupayroll.dto.document.Document;
import com.incubator.edupayroll.dto.document.DocumentCreateInput;
import com.incubator.edupayroll.dto.document.DocumentUpdateInput;
import com.incubator.edupayroll.mapper.document.DocumentMapper;
import com.incubator.edupayroll.service.document.DocumentService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class DocumentController {

  final DocumentService documentService;
  final UserService userService;

  @Autowired
  public DocumentController(DocumentService documentService, UserService userService) {
    this.documentService = documentService;
    this.userService = userService;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<Document, DocumentErrorCode>> updateDocument(
      @PathVariable String id, @RequestBody DocumentUpdateInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var document = documentService.getById(user, UUID.fromString(id));

    var updatedDocument = documentService.update(document, input.getName(), input.getTime());

    return ResponseEntity.ok().body(Response.data(DocumentMapper.toDTO(updatedDocument)).build());
  }

  @PostMapping
  public ResponseEntity<Response<Document, DocumentErrorCode>> createDocument(
      @RequestBody DocumentCreateInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var document = documentService.create(input, user);

    return ResponseEntity.ok().body(Response.data(DocumentMapper.toDTO(document)).build());
  }
}
