package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.entity.user.UserRole;
import com.incubator.edupayroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(
            String name,
            String email,
            String passwordHash
    ) {
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
}
