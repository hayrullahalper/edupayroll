package com.incubator.edupayroll.service.auth;

import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.UserRepository;
import com.incubator.edupayroll.service.email.EmailService;
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
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(
            UserService userService, SchoolService schoolService, PasswordService passwordService, EmailService emailService, UserRepository userRepository) {
        this.userService = userService;
        this.schoolService = schoolService;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public UserEntity login(String email, String password) {
        var user = userService.getByEmail(email);

        if (!passwordService.match(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public void register(String email, String token) {
        if (!userService.existsByEmail(email))
            emailService.sendRegisterConfirmationEmail(email, token);
    }

    public void requestResetPassword(String email, String token) {
        if (userService.existsByEmail(email))
            emailService.sendResetPasswordEmail(email, token);
    }

    public void resetPassword(String email, String password) {
        var user = userService.getByEmail(email);
        var passwordHash = passwordService.hash(password);

        user.setPasswordHash(passwordHash);
        userRepository.save(user);
    }

    public UserEntity completeRegister(
            String firstName,
            String lastName,
            String email,
            String title,
            String password,
            String schoolName,
            String principalName) {

        var passwordHash = passwordService.hash(password);
        var user = userService.create(firstName, lastName, email, passwordHash);

        schoolService.create(user, schoolName, firstName + lastName, title, principalName);

        emailService.sendRegisterCompleteEmail(email, firstName + lastName);

        return user;
    }

}
