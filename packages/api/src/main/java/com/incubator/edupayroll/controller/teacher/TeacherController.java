package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.dto.teacher.*;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.common.response.PageResponse;
import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.common.selection.SelectionType;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

  private final UserService userService;
  private final TeacherService teacherService;

  @Autowired
  public TeacherController(UserService userService, TeacherService teacherService) {
    this.userService = userService;
    this.teacherService = teacherService;
  }

  @GetMapping("")
  public ResponseEntity<PageResponse<Teacher, TeacherErrorCode>> getTeachers(
      @RequestParam("limit") int limit,
      @RequestParam("offset") int offset,
      @RequestParam(value = "query", required = false) Optional<String> query) {
    var user = userService.getAuthenticatedUser();

    var count = teacherService.count(user, query);
    var teachers =
        teacherService.getAll(user, limit, offset, query).stream()
            .map(TeacherMapper::toDTO)
            .toList();

    return ResponseEntity.ok().body(PageResponse.data(teachers).meta(limit, offset, count).build());
  }

  @PutMapping("{id}")
  public ResponseEntity<Response<Teacher, TeacherErrorCode>> updateTeacher(
      @PathVariable String id, @Valid @RequestBody TeacherUpdateInput input) {
    var user = userService.getAuthenticatedUser();
    var teacher = teacherService.getById(user, UUID.fromString(id));

    var updatedTeacher =
        teacherService.update(
            teacher,
            input.getName(),
            input.getBranch(),
            input.getIdNumber(),
            input.getDescription());

    return ResponseEntity.ok().body(Response.data(TeacherMapper.toDTO(updatedTeacher)).build());
  }

  @PostMapping("")
  public ResponseEntity<Response<Teacher, TeacherErrorCode>> createTeacher(
      @Valid @RequestBody TeacherCreateInput input) {
    var user = userService.getAuthenticatedUser();
    var createdTeacher =
        teacherService.create(
            input.getName(), input.getBranch(), input.getIdNumber(), input.getDescription(), user);

    return ResponseEntity.ok().body(Response.data(TeacherMapper.toDTO(createdTeacher)).build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Response<TeacherDeletePayload, TeacherErrorCode>> deleteTeacher(
      @PathVariable String id) {
    var user = userService.getAuthenticatedUser();
    var teacher = teacherService.getById(user, UUID.fromString(id));

    teacherService.remove(teacher);

    return ResponseEntity.ok().body(Response.data(new TeacherDeletePayload(true)).build());
  }

  @DeleteMapping("/bulk")
  public ResponseEntity<Response<TeacherDeletePayload, TeacherErrorCode>> deleteTeachers(
      @Valid @RequestBody TeacherDeleteInput input) {
    var user = userService.getAuthenticatedUser();
    var selectionType = input.getType() != null ? input.getType() : SelectionType.INCLUDE;

    teacherService.bulkRemove(
        user, selectionType, input.getIds().stream().map(UUID::fromString).toList());

    return ResponseEntity.ok().body(Response.data(new TeacherDeletePayload(true)).build());
  }
}
