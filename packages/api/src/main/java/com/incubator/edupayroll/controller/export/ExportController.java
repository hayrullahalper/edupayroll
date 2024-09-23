package com.incubator.edupayroll.controller.export;

import com.incubator.edupayroll.dto.export.Export;
import com.incubator.edupayroll.dto.export.ExportDeleteInput;
import com.incubator.edupayroll.dto.export.ExportDeletePayload;
import com.incubator.edupayroll.dto.export.ExportNameUpdateInput;
import com.incubator.edupayroll.mapper.export.ExportMapper;
import com.incubator.edupayroll.service.export.ExportService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.common.response.PageResponse;
import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.common.selection.SelectionType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exports")
public class ExportController {
  private final UserService userService;
  private final ExportService exportService;

  @Autowired
  public ExportController(UserService userService, ExportService exportService) {
    this.userService = userService;
    this.exportService = exportService;
  }

  @GetMapping("")
  public ResponseEntity<PageResponse<Export, ExportErrorCode>> getExports(
      @RequestParam("limit") int limit,
      @RequestParam("offset") int offset,
      @RequestParam(value = "name", required = false) Optional<String> name) {
    var user = userService.getAuthenticatedUser();

    var count = exportService.count(user, name);
    var exports =
        exportService.getAll(user, limit, offset, name).stream().map(ExportMapper::toDTO).toList();

    return ResponseEntity.ok().body(PageResponse.data(exports).meta(limit, offset, count).build());
  }

  @PutMapping("/{id}/name")
  public ResponseEntity<Response<Export, ExportErrorCode>> updateExportName(
      @PathVariable("id") UUID id, @RequestBody ExportNameUpdateInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var export = exportService.getById(user, id);

    var updated = exportService.updateName(export, input.getName());

    return ResponseEntity.ok().body(Response.data(ExportMapper.toDTO(updated)).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<ExportDeletePayload, ExportErrorCode>> deleteExport(
      @PathVariable("id") UUID id) {
    var user = userService.getAuthenticatedUser();
    var export = exportService.getById(user, id);

    exportService.remove(export);

    return ResponseEntity.ok().body(Response.data(new ExportDeletePayload(true)).build());
  }

  @DeleteMapping("/bulk")
  public ResponseEntity<Response<ExportDeletePayload, ExportErrorCode>> deleteExports(
      @RequestBody ExportDeleteInput input) {

    var user = userService.getAuthenticatedUser();
    var selectionType = input.getType() != null ? input.getType() : SelectionType.INCLUDE;

    exportService.bulkRemove(
        user, selectionType, input.getIds().stream().map(UUID::fromString).toList());

    return ResponseEntity.ok().body(Response.data(new ExportDeletePayload(true)).build());
  }
}
