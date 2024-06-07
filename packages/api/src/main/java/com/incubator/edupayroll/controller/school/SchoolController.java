package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.dto.school.SchoolDTO;
import com.incubator.edupayroll.dto.school.SchoolUpdateDTO;
import com.incubator.edupayroll.mapper.school.SchoolMapper;
import com.incubator.edupayroll.service.school.SchoolService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
public class SchoolController {


    private final UserService userService;
    private final SchoolService schoolService;

    @Autowired
    public SchoolController(UserService userService, SchoolService schoolService) {
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @PutMapping({"", "/"})
    public ResponseEntity<Response<SchoolDTO, ?, SchoolErrorCode>> update(
            @RequestBody SchoolUpdateDTO schoolUpdateDTO
    ) {
        Validation.validate(schoolUpdateDTO);

        var user = userService.getAuthenticatedUser();
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
    public ResponseEntity<Response<SchoolDTO, ?, SchoolErrorCode>> get() {
        var user = userService.getAuthenticatedUser();
        var school = schoolService.getByUser(user);

        return ResponseEntity
                .ok()
                .body(Response.data(SchoolMapper.toDTO(school)));
    }
}
