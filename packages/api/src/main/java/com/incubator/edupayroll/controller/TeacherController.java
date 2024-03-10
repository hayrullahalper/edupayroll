package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dao.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherDAO;

    @Autowired
    public TeacherController(TeacherRepository teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @GetMapping("/")
    public String getTeachers() {
        return teacherDAO.findAll().toString();
    }

    @GetMapping("/{id}")
    public String getTeachers(@PathVariable String id) {
        return teacherDAO.findById(UUID.fromString(id)).toString();
    }

}
