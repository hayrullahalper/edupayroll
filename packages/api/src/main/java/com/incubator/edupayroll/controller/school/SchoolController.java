package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.dto.school.SchoolUpdateDTO;
import com.incubator.edupayroll.service.school.SchoolNotFoundException;
import com.incubator.edupayroll.service.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SchoolUpdateDTO> updateSchool(
            @RequestBody SchoolUpdateDTO schoolUpdateDTO
    ) {
        return ResponseEntity
                .ok()
                .body(schoolUpdateDTO);
    }

//    @GetMapping({"/{id}"})
//    public ResponseEntity<SchoolDTO> getSchool(
//            @PathVariable String id
//    ) {
//        return ResponseEntity
//                .ok()
//                .body(schoolService.getById(UUID.randomUUID()));
//    }

    @ExceptionHandler({SchoolNotFoundException.class})
    public ResponseEntity<String> handleException(SchoolNotFoundException schoolNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(schoolNotFoundException.getMessage());
    }

}
