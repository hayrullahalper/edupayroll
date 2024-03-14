package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dto.teacher.TeacherCreationDTO;
import com.incubator.edupayroll.dto.teacher.TeacherDTO;
import com.incubator.edupayroll.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<TeacherDTO> createTeacher(
            @RequestBody TeacherCreationDTO teacherCreationDTO
    ) {
        var teacher = teacherService.create(teacherCreationDTO);

        return ResponseEntity
                .ok()
                .body(teacher);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<TeacherDTO>> getTeachers(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam("offset") int offset
    ) {
        var teachers = teacherService.getAll(
                Optional.ofNullable(limit).orElse(20),
                offset
        );

        return ResponseEntity
                .ok()
                .body(teachers);
    }

}
