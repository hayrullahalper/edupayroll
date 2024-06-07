package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.dto.teacher.TeacherDTO;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.meta.PageMeta;
import com.incubator.edupayroll.util.response.Response;
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
    public ResponseEntity<Response<TeacherDTO[], PageMeta, ?>> getTeachers(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset
    ) {
        var user = userService.getAuthenticatedUser();

        var count = teacherService.count(user);
        var teachers = teacherService.getAll(user, limit, offset)
                .stream()
                .map(TeacherMapper::toDTO)
                .toArray(TeacherDTO[]::new);


        var pages = (int) Math.ceil((double) count / limit);

        return ResponseEntity
                .ok()
                .body(Response
                        .data(teachers)
                        .meta(PageMeta.of(limit, offset, pages))
                        .build()
                );
    }

}