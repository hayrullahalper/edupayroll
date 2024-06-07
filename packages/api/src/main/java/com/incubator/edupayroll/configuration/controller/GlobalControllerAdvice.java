package com.incubator.edupayroll.configuration.controller;

import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute("user")
    public User populateUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (auth != null && auth.isAuthenticated()) {
                var detail = auth.getPrincipal();

                if (detail instanceof UserDetails) {
                    var email = ((UserDetails) detail).getUsername();
                    return userService.getByEmail(email);
                }

                return null;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
