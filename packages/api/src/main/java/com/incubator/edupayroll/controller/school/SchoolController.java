package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.dto.school.School;
import com.incubator.edupayroll.dto.school.SchoolUpdateInput;
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

    @PutMapping("")
    public ResponseEntity<Response<School, SchoolErrorCode>> updateSchool(
            @RequestBody SchoolUpdateInput input
    ) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        var school = schoolService.getByUser(user);

        var updatedSchool = schoolService.update(
                school,
                input.getName(),
                input.getEditorName(),
                input.getEditorTitle(),
                input.getPrincipalName()
        );

        return ResponseEntity
                .ok()
                .body(Response.data(SchoolMapper.toDTO(updatedSchool)).build());
    }

    @GetMapping("")
    public ResponseEntity<Response<School, SchoolErrorCode>> getSchool() {
        var user = userService.getAuthenticatedUser();
        var school = schoolService.getByUser(user);

        return ResponseEntity
                .ok()
                .body(Response.data(SchoolMapper.toDTO(school)).build());
    }
}
