package com.incubator.edupayroll.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl
    implements org.springframework.security.core.userdetails.UserDetailsService {
  private final UserService userService;

  @Autowired
  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    var user = userService.getByEmail(email);
    return UserDetailsImpl.build(user);
  }
}
