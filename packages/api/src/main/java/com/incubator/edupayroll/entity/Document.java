package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "document")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Document extends Node {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    private List<Export> exports;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    private List<DocumentRow> rows;
}
