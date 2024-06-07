package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.dto.school.SchoolDTO;
import com.incubator.edupayroll.dto.school.SchoolUpdateDTO;
import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.mapper.school.SchoolMapper;
import com.incubator.edupayroll.service.school.SchoolService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PutMapping({"", "/"})
    public ResponseEntity<Response<SchoolDTO, ?, ?>> update(
            @ModelAttribute("user") User user,
            @RequestBody SchoolUpdateDTO schoolUpdateDTO
    ) {
        Validation.validate(schoolUpdateDTO);

        var school = schoolService.getByUser(user);

        var updatedSchool = schoolService.update(
                school,
                schoolUpdateDTO.getName(),
                schoolUpdateDTO.getEditorName(),
                schoolUpdateDTO.getEditorTitle(),
                schoolUpdateDTO.getPrincipalName()
        );

        return ResponseEntity
                .ok()
                .body(Response.data(SchoolMapper.toDTO(updatedSchool)));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Response<SchoolDTO, ?, ?>> get(@ModelAttribute("user") User user) {
        var school = schoolService.getByUser(user);

        return ResponseEntity
                .ok()
                .body(Response.data(SchoolMapper.toDTO(school)));
    }
}
