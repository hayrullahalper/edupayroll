package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "export")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Export extends Node {

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
