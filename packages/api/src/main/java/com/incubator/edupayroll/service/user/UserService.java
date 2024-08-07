package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.entity.user.UserRole;
import com.incubator.edupayroll.repository.UserRepository;
import com.incubator.edupayroll.service.password.PasswordService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private PasswordService passwordService;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity create(String firstName, String lastName, String email, String passwordHash) {
    if (userRepository.existsByEmail(email)) {
      throw UserAlreadyRegisteredException.byEmail(email);
    }

    List<UserRole> roles = List.of(UserRole.USER);

    var user = new UserEntity();

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setRoles(roles);
    user.setPasswordHash(passwordHash);

    return userRepository.saveAndFlush(user);
  }

  public UserEntity getById(String id) {
    var maybeUser = userRepository.findById(UUID.fromString(id));
    return maybeUser.orElseThrow(() -> UserNotFoundException.byId(id));
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

  public void changePassword(UserEntity user, String password, String newPassword) {
    var matched = passwordService.match(password, user.getPasswordHash());

    if (!matched) {
      throw UserPasswordMismatchException.byUser(user);
    }

    user.setPasswordHash(passwordService.hash(newPassword));
    userRepository.saveAndFlush(user);
  }

  @Autowired
  @Lazy
  private void setPasswordService(PasswordService passwordService) {
    this.passwordService = passwordService;
  }

  public UserEntity changeName(UserEntity user, String firstName, String lastName) {

    if (firstName != null) {
      user.setFirstName(firstName);
    }

    if (lastName != null) {
      user.setLastName(lastName);
    }

    return userRepository.saveAndFlush(user);
  }

  public UserEntity changeEmail(UserEntity user, String email, String password) {
    var matched = passwordService.match(password, user.getPasswordHash());

    if (!matched) {
      throw UserPasswordMismatchException.byUser(user);
    }

    if (existsByEmail(email)) {
      throw UserExistsByEmailException.byUser(user);
    }

    user.setEmail(email);
    return userRepository.saveAndFlush(user);
  }
}
