package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


        var pages = (int) Math.ceil((double) count / limit);

        return ResponseEntity
                .ok()
                .body(PageResponse
                        .data(teachers)
                        .meta(limit, offset, pages)
                        .build()
                );
    }

}