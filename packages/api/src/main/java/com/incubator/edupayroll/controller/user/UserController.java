package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/me")
    public ResponseEntity<String> me(@ModelAttribute("user") User user) {
        System.out.println("user = " + user);

        return ResponseEntity.ok().body("me");
    }

}
