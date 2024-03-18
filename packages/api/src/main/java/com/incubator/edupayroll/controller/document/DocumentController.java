package com.incubator.edupayroll.controller.document;

import com.incubator.edupayroll.dto.document.DocumentDTO;
import com.incubator.edupayroll.dto.document.DocumentUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
