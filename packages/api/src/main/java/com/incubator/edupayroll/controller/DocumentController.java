package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dto.document.DocumentDTO;
import com.incubator.edupayroll.dto.document.DocumentUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @PostMapping({"", "/"})
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentDTO documentDTO) {

        return ResponseEntity
                .ok()
                .body(documentDTO);
    }

    @PutMapping({"", "/"})
    public ResponseEntity<DocumentUpdateDTO> updateDocument(@RequestBody DocumentUpdateDTO documentUpdateDTO) {
        return ResponseEntity
                .ok()
                .body(documentUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .body("Deleted: " + id);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<DocumentDTO>> getDocuments(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam("offset") int offset
    ) {
//        var teachers = teacherService.getAll(
//                Optional.ofNullable(limit).orElse(20),
//                offset
//        );

        return ResponseEntity
                .ok()
                .body(new ArrayList<>());
    }

    @PostMapping("/{document_id}/record")
    public ResponseEntity<String> createRecord(@PathVariable("document_id") Long documentId) {

        return ResponseEntity
                .ok()
                .body("Record created.");
    }

    @DeleteMapping("/{document_id}/record/{record_id}")
    public ResponseEntity<String> deleteRecord(@PathVariable("document_id") Long documentId, @PathVariable("record_id") Long record_id) {

        return ResponseEntity
                .ok()
                .body("Record deleted.");
    }

    @PutMapping("/{document_id}/record/{record_id}")
    public ResponseEntity<String> updateRecord(@PathVariable("document_id") Long documentId, @PathVariable("record_id") Long record_id) {

        return ResponseEntity
                .ok()
                .body("Record updated.");
    }

}
