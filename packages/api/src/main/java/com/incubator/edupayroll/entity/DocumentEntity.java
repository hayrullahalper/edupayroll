package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "document")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "document")
    private List<ExportEntity> exports;

    @ToString.Exclude
    @OneToMany(mappedBy = "document")
    private List<RecordEntity> records;

}
