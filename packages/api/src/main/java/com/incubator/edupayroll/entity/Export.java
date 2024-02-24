package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "export")
public class Export extends Node {

    @Column(name = "url", nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;

}
