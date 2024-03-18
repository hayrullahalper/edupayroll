package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.dto.teacher.TeacherCreateDTO;
import com.incubator.edupayroll.dto.teacher.TeacherDTO;
import com.incubator.edupayroll.dto.teacher.TeacherUpdateDTO;
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
            @RequestBody TeacherCreateDTO teacherCreateDTO
    ) {
        var teacher = teacherService.create(teacherCreateDTO);

        return ResponseEntity
                .ok()
                .body(teacher);
    }

    @PutMapping({"", "/"})
    public ResponseEntity<TeacherUpdateDTO> updateTeacher(
            @RequestBody TeacherUpdateDTO teacherUpdateDTO
    ) {
        return ResponseEntity
                .ok()
                .body(teacherUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .body("Deleted: " + id);
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
