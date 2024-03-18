package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.dto.school.SchoolUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @PutMapping({"", "/"})
    public ResponseEntity<SchoolUpdateDTO> updateTeacher(
            @RequestBody SchoolUpdateDTO schoolUpdateDTO
    ) {
        return ResponseEntity
                .ok()
                .body(schoolUpdateDTO);
    }
}
