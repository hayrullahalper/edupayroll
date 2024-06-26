package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.dto.teacher.TeacherCreateInput;
import com.incubator.edupayroll.dto.teacher.TeacherDeletePayload;
import com.incubator.edupayroll.dto.teacher.TeacherUpdateInput;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.PageResponse;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<PageResponse<Teacher, ?>> getTeachers(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset
    ) {
        var user = userService.getAuthenticatedUser();

        var count = teacherService.count(user);
        var teachers = teacherService.getAll(user, limit, offset)
                .stream()
                .map(TeacherMapper::toDTO)
                .toList();

        var page = offset / limit + 1;
        var total = (int) Math.ceil((double) count / limit);

        return ResponseEntity
                .ok()
                .body(PageResponse
                        .data(teachers)
                        .meta(page, limit, total)
                        .build()
                );
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<Teacher, TeacherErrorCode>> updateTeacher(@RequestBody TeacherUpdateInput input, @PathVariable String id) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        var teacher = teacherService.getById(user, UUID.fromString(id));

        var updatedTeacher = teacherService.update(teacher, input.getName(), input.getBranch(), input.getIdentityNo());

        return ResponseEntity.ok()
                .body(Response
                        .data(TeacherMapper.toDTO(updatedTeacher))
                        .build());
    }

    @PostMapping("")
    public ResponseEntity<Response<Teacher, TeacherErrorCode>> createTeacher(@RequestBody TeacherCreateInput input) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        var createdTeacher = teacherService.create(input.getName(), input.getBranch(), input.getIdNumber(), user);

        return ResponseEntity.ok()
                .body(Response
                        .data(TeacherMapper.toDTO(createdTeacher))
                        .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<TeacherDeletePayload, TeacherErrorCode>> deleteTeacher(@PathVariable String id) {
        var user = userService.getAuthenticatedUser();
        var teacher = teacherService.getById(user, UUID.fromString(id));

        teacherService.remove(teacher);

        return ResponseEntity.ok()
                .body(Response
                        .data(new TeacherDeletePayload(true))
                        .build());
    }

}
