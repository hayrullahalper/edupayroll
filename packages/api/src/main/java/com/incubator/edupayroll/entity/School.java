package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "school")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class School extends Node {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "editor_name", nullable = false)
    private String editorName;

    @Column(name = "editor_title", nullable = false)
    private String editorTitle;

    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
