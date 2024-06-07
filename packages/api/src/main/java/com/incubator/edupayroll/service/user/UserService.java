package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.Role;
import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(
            String name,
            String email,
            String passwordHash
    ) {
        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyRegisteredException.byEmail(email);
        }

        List<Role> roles = List.of(Role.USER);

        var user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        user.setPasswordHash(passwordHash);

        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        var maybeUser = userRepository.findByEmail(email);
        return maybeUser.orElseThrow(() -> UserNotFoundException.byEmail(email));
    }
}