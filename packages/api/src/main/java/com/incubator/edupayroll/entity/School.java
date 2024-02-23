package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "School")
public class School extends BaseEntity {

    @Column(name = "principal_name")
    private String principalName;

    @Column(name = "editor_name")
    private String editorName;

    @Column(name = "editor_title")
    private String editorTitle;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


}
