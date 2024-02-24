package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "school")
public class School extends Node {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @Column(name = "editor_name", nullable = false)
    private String editorName;

    @Column(name = "editor_title", nullable = false)
    private String editorTitle;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
