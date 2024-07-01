package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {
  private final UUID id;
  private final String name;
  private final String email;
  private final String passwordHash;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(
      UUID id,
      String name,
      String email,
      String passwordHash,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.authorities = authorities;
    this.passwordHash = passwordHash;
  }

  public static UserDetailsImpl build(UserEntity user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(), user.getName(), user.getEmail(), user.getPasswordHash(), authorities);
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserDetailsImpl that = (UserDetailsImpl) o;

    return id.equals(that.id);
  }
}
