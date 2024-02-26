package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dao.TeacherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherController(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @GetMapping("/")
    public String getTeachers() {
        return teacherDAO.findAll().toString();
    }

    @GetMapping("/{id}")
    public String getTeachers(@PathVariable String id) {
        return teacherDAO.findById(id).toString();
    }

}
