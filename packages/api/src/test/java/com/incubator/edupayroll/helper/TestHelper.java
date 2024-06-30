package com.incubator.edupayroll.helper;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {
    @Autowired
    private Faker faker;

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser() {
        var user = new UserEntity();

        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setPasswordHash(faker.internet().password());

        return userRepository.saveAndFlush(user);
    }
}
