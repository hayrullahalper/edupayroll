package com.incubator.edupayroll.controller.teacher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
//
//    private final TeacherService teacherService;
//    private final UserService userService;
//
//    @Autowired
//    public TeacherController(TeacherService teacherService, UserService userService) {
//        this.teacherService = teacherService;
//        this.userService = userService;
//    }
//
//    @PostMapping({"", "/"})
//    public ResponseEntity<TeacherDTO> createTeacher(
//            @RequestBody TeacherCreateDTO teacherCreateDTO
//    ) {
////        var teacher = teacherService.create(teacherCreateDTO);
//
//        return ResponseEntity
//                .ok()
//                .body(null);
//    }
//
//    @PutMapping({"", "/"})
//    public ResponseEntity<TeacherUpdateDTO> updateTeacher(
//            @RequestBody TeacherUpdateDTO teacherUpdateDTO
//    ) {
//        return ResponseEntity
//                .ok()
//                .body(teacherUpdateDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id) {
//        return ResponseEntity
//                .ok()
//                .body("Deleted: " + id);
//    }
//
//    @GetMapping({"", "/"})
//    public ResponseEntity<List<TeacherDTO>> getTeachers(
//            @RequestParam(value = "limit", required = false) Integer limit,
//            @RequestParam("offset") int offset
//    ) {
//        var teachers = teacherService.getAll(
//                Optional.ofNullable(limit).orElse(20),
//                offset
//        );
//
//        return ResponseEntity
//                .ok()
//                .body(teachers);
//    }

}
