package com.incubator.edupayroll.service.auth;

import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.service.password.PasswordService;
import com.incubator.edupayroll.service.school.SchoolService;
import com.incubator.edupayroll.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final SchoolService schoolService;
    private final PasswordService passwordService;

    @Autowired
    public AuthService(
            UserService userService,
            SchoolService schoolService,
            PasswordService passwordService
    ) {
        this.userService = userService;
        this.schoolService = schoolService;
        this.passwordService = passwordService;
    }

    public UserEntity login(String email, String password) {
        var user = userService.getByEmail(email);

        if (!passwordService.match(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public UserEntity register(
            String name,
            String email,
            String title,
            String password,
            String schoolName,
            String principalName
    ) {
        var passwordHash = passwordService.hash(password);
        var user = userService.create(name, email, passwordHash);

        schoolService.create(
                user,
                schoolName,
                name,
                title,
                principalName
        );

        return user;
    }
}
