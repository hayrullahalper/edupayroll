package com.incubator.edupayroll.entity.token;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity extends BaseEntity {
  @ToString.Exclude
  @Column(name = "hash", nullable = false)
  private String hash;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
}
