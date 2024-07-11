package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.entity.user.UserRole;
import com.incubator.edupayroll.repository.UserRepository;
import com.incubator.edupayroll.service.auth.InvalidCredentialsException;
import com.incubator.edupayroll.service.password.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public UserEntity create(String name, String email, String passwordHash) {
        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyRegisteredException.byEmail(email);
        }

        List<UserRole> roles = List.of(UserRole.USER);

        var user = new UserEntity();

        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        user.setPasswordHash(passwordHash);

        return userRepository.save(user);
    }

    public UserEntity getByEmail(String email) {
        var maybeUser = userRepository.findByEmail(email);
        return maybeUser.orElseThrow(() -> UserNotFoundException.byEmail(email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (auth != null && auth.isAuthenticated()) {
                var detail = auth.getPrincipal();

                if (detail instanceof UserDetails) {
                    var email = ((UserDetails) detail).getUsername();
                    return getByEmail(email);
                }

                return null;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public UserEntity changePassword(
            UserEntity user,
            String oldPassword,
            String newPassword
    ) {
        if (!passwordService.match(oldPassword, user.getPasswordHash()))
            throw new InvalidCredentialsException();

        user.setPasswordHash(passwordService.hash(newPassword));

        return userRepository.save(user);
    }

}
